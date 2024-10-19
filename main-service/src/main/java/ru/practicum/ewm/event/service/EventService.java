package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;

import java.util.Collection;
import java.util.List;

public interface EventService {
    Collection<EventShortDto> findBy(long userId, SearchEventDto searchEventDto);

    EventFullDto create(long userId, NewEventDto newEvent);

    EventFullDto findBy(ParamEventDto paramEventDto);

    EventFullDto update(ParamEventDto paramEventDto, UpdateEventUserRequest updateEvent);

    List<ParticipationRequestDto> findRequest(ParamEventDto paramEventDto);

    EventRequestStatusUpdateResult updateRequest(ParamEventDto paramEventDto,
                                                 EventRequestStatusUpdateRequest updateRequest);
}
