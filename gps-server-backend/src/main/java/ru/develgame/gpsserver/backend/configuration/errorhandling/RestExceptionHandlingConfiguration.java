package ru.develgame.gpsserver.backend.configuration.errorhandling;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.develgame.gpsserver.backend.bean.ProblemDetailEx;
import ru.develgame.gpsserver.backend.exception.AuthFailedException;
import ru.develgame.gpsserver.backend.exception.InvalidArgumentException;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandlingConfiguration extends ResponseEntityExceptionHandler {
    private static final int INVALID_ARGUMENT_ERROR_CODE = 1000;

    private static final int UNAUTHORIZED_ERROR_CODE = 2000;

    @ExceptionHandler(InvalidArgumentException.class)
    public ProblemDetail handleInvalidArgumentException(InvalidArgumentException e) {
        return new ProblemDetailEx(HttpStatus.BAD_REQUEST.value(), INVALID_ARGUMENT_ERROR_CODE, e.getMessage());
    }

    @ExceptionHandler(AuthFailedException.class)
    public ProblemDetail handleAuthFailedException(AuthFailedException e) {
        return new ProblemDetailEx(HttpStatus.UNAUTHORIZED.value(), UNAUTHORIZED_ERROR_CODE, e.getMessage());
    }
}
