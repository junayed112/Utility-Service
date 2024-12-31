package com.reddot.utilityservice.requestmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMoneyResponse {
    private String requestId;
    private String requestFrom;
    private String requesterName;
    private String requestTo;
    private String receiverName;
    private BigDecimal requestedAmount;
    private String transactionStatus;
    private String requestDateTime;
}
