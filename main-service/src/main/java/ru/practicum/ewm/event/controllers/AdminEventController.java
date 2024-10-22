package ru.practicum.ewm.event.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.service.EventService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminEventController {

    private final EventService eventService;

    @GetMapping("/events")
    public Collection<EventShortDto> findAllEvents(@RequestParam(required = false) List<Long> users,
                                                   @RequestParam(required = false) List<EventState> states,
                                                   @RequestParam(required = false) List<Long> category,
                                                   @RequestParam(required = false) LocalDateTime rangeStart,
                                                   @RequestParam(required = false) LocalDateTime rangeEnd,
                                                   @RequestParam(defaultValue = "0") long from,
                                                   @RequestParam(defaultValue = "10")long size
                                                   ) {
        AdminSearchEventDto adminSearchEventDto =
                new AdminSearchEventDto(users, states,category,rangeStart,rangeEnd,from,size);
        log.info("Request to find events {}", adminSearchEventDto);
        return eventService.findBy(adminSearchEventDto);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable long eventId,
                                    @Valid @RequestBody UpdateEventAdminRequest updateEvent) {
        log.info("Request to update event ID = {} by admin, {}", eventId, updateEvent);
        return eventService.update(eventId, updateEvent);
    }
}