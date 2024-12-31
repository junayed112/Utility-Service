package com.reddot.utilityservice.requestmoney.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMoneyDto {
    @NonNull
    private String requestFrom;
    @NonNull
    private String requesterName;
    @NonNull
    private String requestTo;
    @NonNull
    private String receiverName;
    @NonNull
    private BigDecimal requestedAmount;
    @NonNull
    private String pin;
}
