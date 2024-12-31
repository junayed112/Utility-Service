package com.reddot.utilityservice.features.titas.dto;

public enum TitasInfoEnum {
    MERCHANT_NUMBER("xxxxxxx"),
    TRANSACTION_TYPE_TITAS("xxxxxxx"),
    SSL_PAYMENT_ID("xxxxxx");

    public final String value;

    TitasInfoEnum(String value) {
        this.value = value;
    }
}
