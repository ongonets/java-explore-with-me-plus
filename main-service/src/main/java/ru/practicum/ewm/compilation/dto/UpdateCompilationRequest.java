package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

public class UpdateCompilationRequest {

    List<Long> events;

    Boolean pinned;

    @Size(min = 1, max = 50)
    String title;
}
