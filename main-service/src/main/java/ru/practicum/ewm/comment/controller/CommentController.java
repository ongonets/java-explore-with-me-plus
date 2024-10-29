package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentRequest;
import ru.practicum.ewm.comment.service.CommentService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users/{userId}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/events/{eventId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@Valid @RequestBody NewCommentRequest request,
                                    @PathVariable @Positive long userId,
                                    @PathVariable @Positive long eventId) {
        log.info("Received request to add new comment: {}", request.getText());
        return commentService.createComment(request, userId, eventId);
    }
}
