package ru.practicum.ewm.events.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.events.model.EventState;
import ru.practicum.ewm.events.model.Location;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    String annotation;
    long category;
    String description;
    LocalDateTime eventDate;
    Location location;
    long participantLimit;
    boolean requestModeration;
    EventState state;
    boolean paid;
    String title;
}
