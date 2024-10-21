package ru.practicum.ewm.request.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.dto.Location;
import ru.practicum.ewm.event.model.EventState;

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
