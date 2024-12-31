package com.reddot.utilityservice.features.wasa.dto;

public enum WasaInfoEnum {

    MERCHANT_NUMBER("016241112289"),
    TRANSACTION_TYPE_WASA_CORE("UTILITY_BILL_PAYMENT_WASA_SSL"),
    REGEX_ACCOUNT_NUMBER("[a-zA-Z_0-9]{1,30}"),
    REGEX_BILL_NUMBER("[a-zA-Z_0-9]{1,30}"),
    SSL_PAYMENT_ID("3");

    public final String value;

    WasaInfoEnum(String value) {
        this.value = value;
    }

}
