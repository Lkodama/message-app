package com.kodamalabs.messageapp.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ResponseErrorHandler {

  @ExceptionHandler(value = { EntityNotFoundException.class })
  public ResponseEntity handleNotFound(final RuntimeException ex, final WebRequest request) {
      return new ResponseEntity(ex.getMessage(), NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleValidationExceptions(final MethodArgumentNotValidException ex) {
    Map<String, String> validationErrors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      validationErrors.put(fieldName, errorMessage);
    });

    return new ResponseEntity(validationErrors, BAD_REQUEST);
  }
}
