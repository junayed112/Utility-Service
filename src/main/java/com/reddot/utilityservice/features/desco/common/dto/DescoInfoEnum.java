package com.reddot.utilityservice.features.desco.common.dto;

public enum DescoInfoEnum {
    MERCHANT_NUMBER("xxxxxxx"),
    TRANSACTION_TYPE_DESCO("xxxxxxx"),
    SSL_PAYMENT_ID("xxxxxx");

    public final String value;

    DescoInfoEnum(String value){
        this.value = value;
    }

}
