package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentRequest;

public interface CommentService {

    CommentDto createComment(NewCommentRequest request, long userId, long eventId);
}
