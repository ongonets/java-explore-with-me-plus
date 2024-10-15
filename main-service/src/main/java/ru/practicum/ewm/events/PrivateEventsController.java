package ru.practicum.ewm.events;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventsController {

    @GetMapping("/{userId}/events")
    public Collection<EventShortDto> findAllEvents(@PathVariable long userId,
                                                   @RequestParam long from,
                                                   @RequestParam long size) {
        return null;
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto createEvents(@PathVariable long userId, @RequestBody NewEventDto newEvent) {
        return null;
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findEvent(@PathVariable long userId, @PathVariable long eventId) {
        return null;
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable long userId, @RequestBody UpdateEventUserRequest updateEvent) {
        return null;
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ParticipationRequestDto findEventRequest(@PathVariable long userId, @PathVariable long eventId) {
        return null;
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequest(@PathVariable long userId,
                                                      @PathVariable long eventId,
                                                      @RequestBody EventRequestStatusUpdateRequest updateEvent) {
        return null;
    }
}
