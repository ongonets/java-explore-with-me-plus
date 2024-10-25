package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;

import java.util.Collection;

public interface EventService {
    Collection<EventShortDto> findBy(PrivateSearchEventDto privateSearchEventDto);

    EventFullDto findBy(ParamEventDto paramEventDto, String ip);

    Collection<EventShortDto> findBy(AdminSearchEventDto adminSearchEventDto);

    EventFullDto findPublicEventById(long id);

    EventFullDto create(long userId, NewEventDto newEvent);

    EventFullDto update(ParamEventDto paramEventDto, UpdateEventUserRequest updateEvent);

    EventFullDto update(long eventId, UpdateEventUserRequest updateEvent);
}
