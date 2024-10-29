package ru.practicum.ewm.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewCommentRequest {
    @NotBlank @NotNull
    private String text;
}
