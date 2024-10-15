package ru.practicum.ewm.events.dto;

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
public class EventShortDto {
    String annotation;
    CategoryDto category;
    long confirmedRequests;
    LocalDateTime eventDate;
    long id;
    UserShortDto initiator;
    boolean paid;
    String title;
    long views;
}
