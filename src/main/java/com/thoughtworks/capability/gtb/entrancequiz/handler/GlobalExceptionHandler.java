package com.thoughtworks.capability.gtb.entrancequiz.handler;

import com.thoughtworks.capability.gtb.entrancequiz.common.model.ErrorResult;
import com.thoughtworks.capability.gtb.entrancequiz.exception.ExceptionEnum;
import com.thoughtworks.capability.gtb.entrancequiz.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity studentException(UserNotExistException studentException) {
        ErrorResult errorResult = getErrorResult(HttpStatus.NOT_FOUND.value(),
                studentException.getExceptionEnum().getError(),
                studentException.getExceptionEnum().getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResult);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintException(ConstraintViolationException ex) {
        ErrorResult errorResult = getErrorResult(HttpStatus.BAD_REQUEST.value(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getError(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handle(MethodArgumentNotValidException ex) {
        ErrorResult errorResult = getErrorResult(HttpStatus.BAD_REQUEST.value(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getError(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResult> requestParameterException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String message = parameterName + "是必填项";
        ErrorResult errorResult = getErrorResult(HttpStatus.BAD_REQUEST.value(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getError(),
                ExceptionEnum.REQUEST_PARAMETER_NOT_MATCH.getMessage(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    private ErrorResult getErrorResult(Integer status, String error,
                                       String message, Instant date) {
        return ErrorResult.builder()
                .status(status).error(error)
                .date(date).message(message).build();
    }
}
