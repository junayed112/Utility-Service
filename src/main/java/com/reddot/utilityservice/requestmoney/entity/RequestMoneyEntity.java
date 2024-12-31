package com.reddot.utilityservice.requestmoney.entity;

import com.reddot.utilityservice.common.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "money_requests")
public class RequestMoneyEntity extends BaseEntity {
    private String requestId;
    private String requestFrom;
    private String requesterName;
    private String requestTo;
    private String receiverName;
    private BigDecimal requestedAmount;
    private String transactionStatus;
}
