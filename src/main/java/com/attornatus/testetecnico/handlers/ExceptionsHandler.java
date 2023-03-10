package com.attornatus.testetecnico.handlers;

import com.attornatus.testetecnico.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(RuntimeException exception) {
        return new ResponseEntity<String>(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}