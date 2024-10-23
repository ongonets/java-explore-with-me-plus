package ru.practicum.ewm.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.service.EventService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping("/{userId}/events")
    public Collection<EventShortDto> findAllEvents(@PathVariable long userId,
                                                   @RequestParam long from,
                                                   @RequestParam long size) {
        log.info("Request to find user events {}", userId);
        SearchEventDto paramEventsDto = new SearchEventDto(from, size);
        return eventService.findBy(userId, paramEventsDto);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto createEvents(@PathVariable long userId, @RequestBody NewEventDto newEvent) {
        log.info("Request to create event {} by user {}", newEvent,userId);
        return eventService.create(userId, newEvent);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findEvent(@PathVariable long userId, @PathVariable long eventId) {
        ParamEventDto paramEventDto = new ParamEventDto(userId, eventId);
        log.info("Request to find event {}", paramEventDto);
        return eventService.findBy(paramEventDto);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @RequestBody UpdateEventUserRequest updateEvent) {
        ParamEventDto paramEventDto = new ParamEventDto(userId, eventId);
        log.info("Request to update event {}, {}", paramEventDto, updateEvent);
        return eventService.update(paramEventDto, updateEvent);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ParticipationRequestDto findEventRequest(@PathVariable long userId, @PathVariable long eventId) {
        ParamEventDto paramEventDto = new ParamEventDto(userId, eventId);
        log.info("Request to find eventRequests {}", paramEventDto);
        return eventService.findRequest(paramEventDto);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequest(@PathVariable long userId,
                                                      @PathVariable long eventId,
                                                      @RequestBody EventRequestStatusUpdateRequest updateEvent) {
        ParamEventDto paramEventDto = new ParamEventDto(userId, eventId);
        log.info("Request to update eventRequests {}", paramEventDto);
        return eventService.updateRequest(paramEventDto);
    }
}