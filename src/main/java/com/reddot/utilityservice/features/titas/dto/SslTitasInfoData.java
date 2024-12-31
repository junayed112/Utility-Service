package com.reddot.utilityservice.features.titas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SslTitasInfoData {
    private String transactionId;
    private String invoiceNo;
    private String customerCode;
    private String customerName;
    private String phoneNumber;
    private String zoneCode;
    private BigDecimal billAmount;
    private BigDecimal stampAmount;
    private BigDecimal totalAmount;
    private String operator;
    private String branchRoutingNo;
    private BigDecimal sourceTaxAmount;
    private String chalanNo;
    private Date chalanDate;
    private String chalanBank;
    private String billType;
    private String utilityAuthKey;
    private String utilitySecretKey;
    private String branchCode;
}
