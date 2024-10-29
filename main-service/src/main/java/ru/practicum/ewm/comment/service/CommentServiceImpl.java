package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.params.CreateCommentParams;
import ru.practicum.ewm.comment.dto.params.DeleteCommentParams;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.errorHandler.exception.AccessForbiddenException;
import ru.practicum.ewm.errorHandler.exception.NotFoundException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto createComment(CreateCommentParams params) {
        User author = getUser(params.getUserId());
        Event event = getEvent(params.getEventId());
        Comment comment = commentMapper.mapToComment(params.getRequest(), author, event);
        comment = commentRepository.save(comment);
        log.info("Comment {} added to event {}", comment.getText(), event.getTitle());
        return commentMapper.mapToCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(DeleteCommentParams params) {
        User author = getUser(params.getUserId());
        Event event = getEvent(params.getEventId());
        Comment comment = getComment(params.getCommentId());
        validateCommentAuthor(author, comment);
        commentRepository.delete(comment);
        log.info("Comment {} was deleted from event {} by user {}",
                comment.getText(), event.getTitle(), author.getName());
    }

    private void validateCommentAuthor(User author, Comment comment) {
        if (!author.getId().equals(comment.getAuthor().getId())) {
            log.error("User with ID = {} has no rights to delete comment ID = {}", author.getId(), comment.getId());
            throw new AccessForbiddenException("No rights to delete comment");
        }

    }

    private Comment getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("Not found comment with ID = {}", commentId);
                    return new NotFoundException(String.format("Not found comment with ID = %d", commentId));
                });
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Not found user with ID = {}", userId);
                    return new NotFoundException(String.format("Not found user with ID = %d", userId));
                });
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Not found event with ID = {}", eventId);
                    return new NotFoundException(String.format("Not found event with ID = %d", eventId));
                });
    }

}
