package ru.practicum.ewm.events;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.events.service.EventsService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventsController {

    private final EventsService eventsService;

    @GetMapping("/{userId}/events")
    public Collection<EventShortDto> findAllEvents(@PathVariable long userId,
                                                   @RequestParam long from,
                                                   @RequestParam long size) {
        log.info("Request to find user events {}", userId);
        SearchEventsDto paramEventsDto = new SearchEventsDto(from, size);
        return eventsService.findBy(userId, paramEventsDto);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto createEvents(@PathVariable long userId, @RequestBody NewEventDto newEvent) {
        log.info("Request to create event {} by user {}", newEvent,userId);
        return eventsService.create(userId, newEvent);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findEvent(@PathVariable long userId, @PathVariable long eventId) {
        ParamEventsDto paramEventsDto = new ParamEventsDto(userId, eventId);
        log.info("Request to find event {}", paramEventsDto);
        return eventsService.findBy(paramEventsDto);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @RequestBody UpdateEventUserRequest updateEvent) {
        ParamEventsDto paramEventsDto = new ParamEventsDto(userId, eventId);
        log.info("Request to update event {}, {}", paramEventsDto, updateEvent);
        return eventsService.update(paramEventsDto, updateEvent);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ParticipationRequestDto findEventRequest(@PathVariable long userId, @PathVariable long eventId) {
        ParamEventsDto paramEventsDto = new ParamEventsDto(userId, eventId);
        log.info("Request to find eventRequests {}", paramEventsDto);
        return eventsService.findRequest(paramEventsDto);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequest(@PathVariable long userId,
                                                      @PathVariable long eventId,
                                                      @RequestBody EventRequestStatusUpdateRequest updateEvent) {
        ParamEventsDto paramEventsDto = new ParamEventsDto(userId, eventId);
        log.info("Request to update eventRequests {}", paramEventsDto);
        return eventsService.updateRequest(paramEventsDto);
    }
}
