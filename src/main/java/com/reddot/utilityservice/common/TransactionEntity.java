package com.reddot.utilityservice.common;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity{

    private String transactionId;

    private String featureCode;
    private String billType;
    private String fromAccount;
    private String toAccount;
    private String transactionType;
    private String sslTransactionStatus;
    private String sslTransactionStatusCode;

    private String utilityAuthKey;
    private String utilitySecreteKey;

//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
    private String billerResponseFetch;

//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
    private String billerResponsePayment;


    private String notificationNumber;

    private String coreTxnId;
    private String coreFee;
    private String coreCommission;
    private String coreTransactionStatus;
    private String coreTransactionStatusCode;
    private BigDecimal amount;

    //titas metered unique constant
    private String invoiceNumber;

    //titas non-metered unique constant
    private String customerCode;

    //wasa unique constant
    private String billNo;

    //common constant for all feature
    private String month;

    //Desco unique constant
    private String customerAccount;
}
