package ru.practicum.ewm.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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

    @Size(min = 20, max = 2000)
    @NotBlank
    String annotation;

    @Positive
    long category;

    @Size(min = 20, max = 7000)
    @NotBlank
    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    LocalDateTime eventDate;

    Location location;

    @PositiveOrZero
    long participantLimit;

    boolean requestModeration;

    EventState state;

    boolean paid;

    @Size(min = 3, max = 120)
    @NotBlank
    String title;
}
