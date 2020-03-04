package com.springboot.update.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(INTERNAL_SERVER_ERROR.value())
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity(asList(exceptionResponse), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> userNotfoundException(Exception ex, WebRequest request) {


        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND.value())
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity(asList(exceptionResponse), NOT_FOUND);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Object> errors = ex.getBindingResult().getFieldErrors().stream().map(e -> {
            return new ErrorArgumentValidation(e.getField(), e.getRejectedValue().toString(), e.getDefaultMessage());
        }).collect(Collectors.toList());
        return new ResponseEntity(errors, UNPROCESSABLE_ENTITY);
    }

}
