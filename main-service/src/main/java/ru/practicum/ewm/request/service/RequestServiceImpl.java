package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.errorHandler.exception.ConflictDataException;
import ru.practicum.ewm.errorHandler.exception.NotFoundException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    public List<ParticipationRequestDto> findRequest(long userId) {
        User user = getUser(userId);
        List<Request> requests = requestRepository.findAllByUser(user);
        return requestMapper.mapToDto(requests);
    }

    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        User user = getUser(userId);
        Event event = getEvent(eventId);
        validateRequest(user, event);
        Request request = new Request(event, user);
        if(!event.isRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        requestRepository.save(request);
        return requestMapper.mapToDto(request);
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        User user = getUser(userId);
        Request request = getRequest(requestId, user);
        requestRepository.updateStatus(RequestStatus.CANCELED, request);
        return requestMapper.mapToDto(request);
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Not found user with ID = {}", userId);
                    return new NotFoundException(String.format("Not found user with ID = %d", userId));
                });
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Not found event with ID = {}", eventId);
                    return new NotFoundException(String.format("Not found event with ID = %d", eventId));
                });
    }

    private Request getRequest(long requestId, User user) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> {
                    log.error("Not found request with ID = {}", requestId);
                    return new NotFoundException(String.format("Not found request with ID = %d", requestId));
                });
        isUsersRequest(user, request);
        return request;
    }

    private void validateRequest(User user, Event event) {
        List<Request> requests = requestRepository.findAllByEvent(event);
        isRepeatedRequest(user, requests);
        isUserEqualsEventInitiator(user, event);
        isEventPublished(event);
        isRequestLimitReached(event, requests);
    }

    private void isRepeatedRequest(User user, List<Request> requests) {
        if( requests.stream().anyMatch(request -> request.getUser() == user)) {
            log.error("Request by user ID = {}  is repeated", user.getId());
            throw  new ConflictDataException(
                    String.format("Request by user ID = %d  is repeated", user.getId()));
        }
    }

    private void isUserEqualsEventInitiator(User user, Event event) {
        if (event.getInitiator().equals(user)) {
            log.error("User ID = {} is initiator of event ID = {}", user.getId(), event.getId());
            throw new ConflictDataException(
                    String.format("User ID = %d is initiator of event ID = %d", user.getId(), event.getId()));
        }
    }

    private void isEventPublished(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            log.error("Event ID = {} is not published", event.getId());
            throw new ConflictDataException(
                    String.format("Event ID = %d is not published", event.getId()));
        }
    }

    private void isRequestLimitReached(Event event, List<Request> requests) {
        long count = requests.stream().filter(request -> request.getStatus().equals(RequestStatus.CONFIRMED)).count();
        long limit = event.getParticipantLimit();
        if (limit != 0 && count > limit) {
            log.error("Event ID = {} request limit is reached", event.getId());
            throw new ConflictDataException(
                    String.format("Event ID = %d request limit is reached", event.getId()));
        }
    }

    private void isUsersRequest(User user, Request request) {
        if (request.getUser() != user) {
            log.error("User ID = {} is not own request ID = {}", user.getId(), request.getId());
            throw new NotFoundException(
                    String.format("Event ID = %d is not own request ID = %d", user.getId(), request.getId()));
        }
    }
}
