package ru.practicum.ewm.events.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.category.CategoryDto;
import ru.practicum.ewm.events.model.EventState;
import ru.practicum.ewm.user.UserShortDto;

import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    String annotation;
    CategoryDto category;
    long confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    long id;
    UserShortDto initiator;
    Location location;
    long participantLimit;
    LocalDateTime publishedOn;
    boolean requestModeration;
    EventState state;
    boolean paid;
    String title;
    long views;
}
