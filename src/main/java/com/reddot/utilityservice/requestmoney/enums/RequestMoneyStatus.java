package com.reddot.utilityservice.requestmoney.enums;

public enum RequestMoneyStatus {
    PENDING("Pending"),
    PROCESSED("Processed"),
    DECLINED("Declined");

    public final String value;

    RequestMoneyStatus(String value){
        this.value = value;
    }
}
