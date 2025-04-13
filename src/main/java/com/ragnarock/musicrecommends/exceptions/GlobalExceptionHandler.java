package com.ragnarock.musicrecommends.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnExistedItemException.class)
    public ResponseEntity<AppError> handleResNotFoundException(
            UnExistedItemException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND,
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<AppError> handleConflictException(
            UnExistedItemException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT,
                e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        log.error("Ошибка валидации: {}", errors, ex);
        return new ResponseEntity<>(new ValidError(HttpStatus.BAD_REQUEST, errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AppError> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {
        log.error("Ошибка парсинга входного JSON: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST,
                "Ошибка формата JSON: " + ex.getMostSpecificCause()
                        .getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<AppError> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        log.error("Ошибка типа аргумента запроса: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST,
                "Неверный тип значения для поля '" + ex.getName() + "'"), HttpStatus.BAD_REQUEST);
    }
}