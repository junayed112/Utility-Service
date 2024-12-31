package com.reddot.utilityservice.custom_exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException  {

    private CustomError customError;

    public ApplicationException(CustomError customError){
        super();
        this.customError = customError;
    }
}