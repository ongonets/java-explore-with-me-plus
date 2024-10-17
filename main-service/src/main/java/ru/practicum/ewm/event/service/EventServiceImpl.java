package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.errorHandler.exception.NotFoundException;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Override
    public Collection<EventShortDto> findBy(long userId, SearchEventDto searchEventDto) {
        return null;
    }

    @Override
    public EventFullDto create(long userId, NewEventDto newEvent) {
        User user = getUser(userId);
        Category category = getCategory(newEvent.getCategory());




        return null;
    }

    @Override
    public EventFullDto findBy(ParamEventDto paramEventDto) {
        return null;
    }

    @Override
    public EventFullDto update(ParamEventDto paramEventDto, UpdateEventUserRequest updateEvent) {
        return null;
    }

    @Override
    public ParticipationRequestDto findRequest(ParamEventDto paramEventDto) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateRequest(ParamEventDto paramEventDto) {
        return null;
    }

    private Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Not found category with ID = {}", categoryId);
                    return new NotFoundException(String.format("Not found user category ID = %d", categoryId));
                });
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Not found user with ID = {}", userId);
                    return new NotFoundException(String.format("Not found user with ID = %d", userId));
                });
    }
}
