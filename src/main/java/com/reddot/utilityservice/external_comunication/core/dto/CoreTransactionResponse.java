package com.reddot.utilityservice.external_comunication.core.dto;

import lombok.Data;

@Data
public class CoreTransactionResponse extends CoreGatewayBaseResponse {
    private String status;
    private String txnId;
    private String amount;
    private String fee;
    private String commission;
    private String balanceFrom;
    private String balanceTo;
    private String ticket;
    private String traceNo;
    private String fromAccount;
    private String toAccount;
    private String txnTime;
    private String txnType;
    private String txnTypeName;
    private Double balance;
}
