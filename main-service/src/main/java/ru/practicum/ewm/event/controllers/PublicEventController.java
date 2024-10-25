package ru.practicum.ewm.event.controllers;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.service.EventService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventFullDto findEventById(@Positive @PathVariable long id) {
        log.info("Received public request to find event with ID = {}", id);
        return eventService.findPublicEventById(id);
    }
}
