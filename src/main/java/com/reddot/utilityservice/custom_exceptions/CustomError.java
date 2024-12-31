package com.reddot.utilityservice.custom_exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomError {

    private String statusCode;
    private String message;

    public CustomError(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
