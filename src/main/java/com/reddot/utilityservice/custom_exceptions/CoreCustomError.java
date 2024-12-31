package com.reddot.utilityservice.custom_exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoreCustomError {

    private String code;
    private String message;

    public CoreCustomError(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
