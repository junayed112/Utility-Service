package com.reddot.utilityservice.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    private final Integer statusCode;
    public BadRequestException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
