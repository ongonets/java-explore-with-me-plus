package ru.practicum.ewm.event.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    String annotation;
    long category;
    String description;
    LocalDateTime eventDate;
    Location location;
    boolean paid;
    long participantLimit;
    boolean requestModeration;
    String title;
}
