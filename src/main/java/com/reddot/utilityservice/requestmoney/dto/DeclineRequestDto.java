package com.reddot.utilityservice.requestmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclineRequestDto {
    @NonNull
    private String requestId;
}
