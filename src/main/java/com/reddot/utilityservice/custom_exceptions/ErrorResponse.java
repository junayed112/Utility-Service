package com.reddot.utilityservice.custom_exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;
}
