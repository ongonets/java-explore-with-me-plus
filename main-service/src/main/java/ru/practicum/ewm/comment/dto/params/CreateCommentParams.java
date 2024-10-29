package ru.practicum.ewm.comment.dto.params;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.comment.dto.NewCommentRequest;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCommentParams {
    NewCommentRequest request;
    long userId;
    long eventId;
}
