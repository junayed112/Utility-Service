package com.reddot.utilityservice.custom_exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestRepositoryExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException e) {
        return ResponseEntity.status(400).body(e.getCustomError());
    }
}
