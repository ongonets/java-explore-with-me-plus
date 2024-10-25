package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;

import java.util.Collection;

public interface EventService {
    Collection<EventShortDto> findBy(PrivateSearchEventDto privateSearchEventDto);

    EventFullDto findBy(ParamEventDto paramEventDto, String ip);

    Collection<EventFullDto> findBy(AdminSearchEventDto adminSearchEventDto);

    EventFullDto findEventByIdPublic(long id);

    Collection<EventShortDto> findEventsPublic(PublicSearchEventParams params);

    EventFullDto create(long userId, NewEventDto newEvent);

    EventFullDto update(ParamEventDto paramEventDto, UpdateEventUserRequest updateEvent);

    EventFullDto update(long eventId, UpdateEventUserRequest updateEvent);
}
