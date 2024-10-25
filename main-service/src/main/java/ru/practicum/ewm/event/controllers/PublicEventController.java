package ru.practicum.ewm.event.controllers;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.PublicSearchEventParams;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.service.EventService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    public Collection<EventShortDto> findEvents(@RequestParam String text,
                                                @RequestParam List<@Positive Long> categories,
                                                @RequestParam Boolean paid,
                                                @RequestParam LocalDateTime rangeStart,
                                                @RequestParam LocalDateTime rangeEnd,
                                                @RequestParam boolean onlyAvailable,
                                                @RequestParam Sort sort,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        PublicSearchEventParams params = new PublicSearchEventParams(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        log.info("Received public request to find events with params: {}", params);
        return eventService.findEventsPublic(params);
    }

    @GetMapping("/{id}")
    public EventFullDto findEventById(@Positive @PathVariable long id) {
        log.info("Received public request to find event with ID = {}", id);
        return eventService.findEventByIdPublic(id);
    }
}
