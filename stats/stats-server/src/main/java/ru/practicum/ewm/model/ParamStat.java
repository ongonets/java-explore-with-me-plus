package ru.practicum.ewm.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ParamStat {
    LocalDateTime start;
    LocalDateTime end;
    String[] uris;
    Boolean unique;
}
