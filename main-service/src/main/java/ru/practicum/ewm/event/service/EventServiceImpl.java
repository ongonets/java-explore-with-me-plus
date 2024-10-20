package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.StatClient;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.ParamDto;
import ru.practicum.ewm.dto.StatDto;
import ru.practicum.ewm.errorHandler.exception.NotFoundException;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.mapper.RequestMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.Request;
import ru.practicum.ewm.event.model.RequestStatus;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final StatClient statClient;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public Collection<EventShortDto> findBy(PrivateSearchEventDto privateSearchEventDto) {
        User user = getUser(privateSearchEventDto.getUserId());
        List<Event> events = eventRepository
                .findByInitiator(user, privateSearchEventDto.getSize(), privateSearchEventDto.getFrom());
        Map<Long, Long> countConfirmedRequest = getCountConfirmedRequest(events);
        Map<Long, Long> stat = getStat(events);
        addHit("/events",privateSearchEventDto.getIp());
        return events.stream()
                .map(event ->  eventMapper.mapToShortDto(event,
                        stat.get(event.getId()),
                        countConfirmedRequest.get(event.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto create(long userId, NewEventDto newEvent) {
        User user = getUser(userId);
        Category category = getCategory(newEvent.getCategory());
        Event event = eventMapper.map(newEvent);
        event.setCategory(category);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        eventRepository.save(event);
        log.info("Event save {}", event);
        return eventMapper.mapToFullDto(event, null, null);
    }

    @Override
    public EventFullDto findBy(ParamEventDto paramEventDto, String ip) {
        Event event = getEvent(paramEventDto);
        Map<Long, Long> countConfirmedRequest = getCountConfirmedRequest(List.of(event));
        Map<Long, Long> stat = getStat(List.of(event));
        addHit(createEventUri(event), ip);
        return eventMapper.mapToFullDto(event, stat.get(event.getId()), countConfirmedRequest.get(event.getId()));
    }

    @Override
    @Transactional
    public EventFullDto update(ParamEventDto paramEventDto, UpdateEventUserRequest updateEvent) {
        Event event = getEvent(paramEventDto);
        Category category = getCategory(updateEvent.getCategory());
        Event updatedEvent = eventMapper.update(event, updateEvent, category);
        eventRepository.save(updatedEvent);
        Map<Long, Long> countConfirmedRequest = getCountConfirmedRequest(List.of(event));
        return eventMapper.mapToFullDto(updatedEvent, null, countConfirmedRequest.get(event.getId()));
    }

    @Override
    public List<ParticipationRequestDto> findRequest(ParamEventDto paramEventDto) {
        Event event = getEvent(paramEventDto);
        List<Request> requests = requestRepository.findAllByEvent(event);
        return requestMapper.mapToDto(requests);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequest(ParamEventDto paramEventDto,
                                                        EventRequestStatusUpdateRequest updateRequest) {
        Event event = getEvent(paramEventDto);
        requestRepository.updateStatus(updateRequest.getStatus(), updateRequest.getRequestIds());
        List<Request> requests = requestRepository.findAllByEvent(event);
        List<Request> confirmedRequests = requests.stream()
                .filter(request -> request.getStatus().equals(RequestStatus.CONFIRMED)).toList();
        List<Request> rejectedRequests = requests.stream()
                .filter(request -> request.getStatus().equals(RequestStatus.REJECTED)).toList();
        return requestMapper.mapToRequestStatus(confirmedRequests, rejectedRequests);
    }

    private Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Not found category with ID = {}", categoryId);
                    return new NotFoundException(String.format("Not found category ID = %d", categoryId));
                });
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Not found user with ID = {}", userId);
                    return new NotFoundException(String.format("Not found user with ID = %d", userId));
                });
    }

    private Event getEvent(ParamEventDto paramEventDto) {
        long userId = paramEventDto.getUserId();
        long eventId = paramEventDto.getEventId();
        User user = getUser(userId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Not found event with ID = {}", eventId);
                    return new NotFoundException(String.format("Not found event with ID = %d", eventId));
                });
        if (event.getInitiator() != user) {
            log.error("Not found event with ID = {}", eventId);
            throw new NotFoundException(
                    String.format("Not found event with ID = %d", eventId));
        }
        return event;
    }

    private Map<Long, Long> getCountConfirmedRequest(List<Event> events) {
        return requestRepository.findConfirmedRequest(events)
                .collect(Collectors.toMap(RequestCountDto::getEventId, RequestCountDto::getCount));
    }

    private Map<Long, Long> getStat(List<Event> events) {
        List<String> uris = events.stream().map(this::createEventUri).toList();
        String start = events.stream().map(Event::getCreatedOn).sorted().findFirst().get().format(dateTimeFormatter);
        String end = LocalDateTime.now().format(dateTimeFormatter);
        ParamDto paramDto = new ParamDto(start, end, uris, false);
        List<StatDto> statDto = statClient.stat(paramDto);
        return statDto.stream().map(dto -> new StatEventDto(parseUri(dto.getUri()), dto.getHits()))
                .collect(Collectors.toMap(StatEventDto::getEventId, StatEventDto::getHits));
    }

    private void addHit(String uri, String ip) {
        HitDto hitDto = new HitDto(0,
                "ewm-main-service",
                uri,
                ip,
                LocalDateTime.now().format(dateTimeFormatter));
        statClient.hit(hitDto);
    }

    private int parseUri(String uri) {
        String[] split = uri.split("/");
        return Integer.parseInt(split[2]);
    }

    private String createEventUri(Event event) {
        return String.format("/event/%d", event.getId());
    }
}
