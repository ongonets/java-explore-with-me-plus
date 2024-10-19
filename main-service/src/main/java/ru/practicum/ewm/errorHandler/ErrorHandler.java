package ru.practicum.ewm.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.errorHandler.exception.ConditionsNotMetException;
import ru.practicum.ewm.errorHandler.exception.ConflictDataException;
import ru.practicum.ewm.errorHandler.exception.NotFoundException;
import ru.practicum.ewm.errorHandler.exception.ValidationException;
import ru.practicum.ewm.errorHandler.model.ApiError;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException e) {
        return new ApiError(getStackTrace(e), e.getMessage(),
                "The required object was not found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationException e) {
        return new ApiError(getStackTrace(e), e.getMessage(),
                "Incorrectly made request.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleConditionsNotMetException(ConditionsNotMetException e) {
        return new ApiError(getStackTrace(e), e.getMessage(),
                "For the requested operation the conditions are not met.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictDataException(ConflictDataException e) {
        return new ApiError(getStackTrace(e), e.getMessage(),
                "Integrity constraint has been violated.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(Throwable e) {
        return new ApiError(getStackTrace(e), e.getMessage(),
                "Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getStackTrace(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
