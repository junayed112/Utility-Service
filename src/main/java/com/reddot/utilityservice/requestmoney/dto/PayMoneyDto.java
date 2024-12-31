package com.reddot.utilityservice.requestmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayMoneyDto{
    @NonNull
    private String requestId;
    @NonNull
    private String pin;
    @NonNull
    private String transactionType;
}
