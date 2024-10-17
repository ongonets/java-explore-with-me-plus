package ru.practicum.ewm.events.service;

import ru.practicum.ewm.events.dto.*;

import java.util.Collection;

public interface EventsService {
    Collection<EventShortDto> findBy(long userId, SearchEventsDto searchEventsDto);

    EventFullDto create(long userId, NewEventDto newEvent);

    EventFullDto findBy(ParamEventsDto paramEventsDto);

    EventFullDto update(ParamEventsDto paramEventsDto, UpdateEventUserRequest updateEvent);

    ParticipationRequestDto findRequest(ParamEventsDto paramEventsDto);

    EventRequestStatusUpdateResult updateRequest(ParamEventsDto paramEventsDto);
}
