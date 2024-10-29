package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.params.CreateCommentParams;
import ru.practicum.ewm.comment.dto.params.DeleteCommentParams;

public interface CommentService {

    CommentDto createComment(CreateCommentParams params);

    void deleteComment(DeleteCommentParams params);
}
