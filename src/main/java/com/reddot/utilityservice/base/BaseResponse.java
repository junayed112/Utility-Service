package com.reddot.utilityservice.base;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BaseResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
