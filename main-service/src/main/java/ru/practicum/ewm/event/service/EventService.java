package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;

import java.util.Collection;

public interface EventService {
    Collection<EventShortDto> findBy(PrivateSearchEventDto privateSearchEventDto);

    EventFullDto create(long userId, NewEventDto newEvent);

    EventFullDto findBy(ParamEventDto paramEventDto, String ip);

    EventFullDto update(ParamEventDto paramEventDto, UpdateEventUserRequest updateEvent);



    Collection<EventShortDto> findBy(AdminSearchEventDto adminSearchEventDto);

    EventFullDto update(long eventId, UpdateEventUserRequest updateEvent);
}
