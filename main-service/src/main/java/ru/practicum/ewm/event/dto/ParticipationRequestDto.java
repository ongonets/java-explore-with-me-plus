package ru.practicum.ewm.event.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.model.RequestStatus;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    LocalDateTime created;
    long event;
    long id;
    long requester;
    RequestStatus status;
}
