package com.reddot.utilityservice.external_comunication.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoreTransactionRequest {
    private String fromAc;
    private String toAc;
    private String txnType;
    private String pin;
    private BigDecimal amount;
}
