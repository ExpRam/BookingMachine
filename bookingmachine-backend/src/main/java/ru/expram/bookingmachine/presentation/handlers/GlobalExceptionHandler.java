package ru.expram.bookingmachine.presentation.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.expram.bookingmachine.domain.common.BaseException;
import ru.expram.bookingmachine.presentation.handlers.exceptions.BaseListedSpringException;
import ru.expram.bookingmachine.presentation.handlers.exceptions.BaseSpringException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<BaseSpringException> baseExceptionHandler(BaseException exception, WebRequest request) {
        final String path = getPath(request);

        final BaseSpringException baseSpringException = new BaseSpringException(
                exception.getErrorCode(),
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                path
        );

        return new ResponseEntity<>(baseSpringException, HttpStatus.valueOf(exception.getErrorCode()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BaseListedSpringException> methodArgumentNotValidExceptionHandler
            (MethodArgumentNotValidException exception, WebRequest request) {
        final List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(field -> "%s %s".formatted(field.getField(), field.getDefaultMessage())).collect(Collectors.toList());
        final String path = getPath(request);

        final BaseListedSpringException baseSpringException = new BaseListedSpringException(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                path
        );

        return new ResponseEntity<>(baseSpringException, HttpStatus.BAD_REQUEST);
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
