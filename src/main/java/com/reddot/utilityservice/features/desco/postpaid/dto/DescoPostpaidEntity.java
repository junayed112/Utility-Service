package com.reddot.utilityservice.features.desco.postpaid.dto;

import com.reddot.utilityservice.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "desco_postpaid")
public class DescoPostpaidEntity extends BaseEntity {

    private String transactionId;
    private String billno;
    @Column(name = "bill_type")
    private String billType;
    private String lid;
    private String status;
    private String status_code;
    private String account_number;
    private String bill_number;
    private String zone_code;
    private String due_date;
    private String organization_code;
    private String tariff;
    private String bill_amount;
    private String vat_amount;
    private String stamp_amount;
    private String lpc_amount;
    private String total_amount;

//  private String userType;


}
