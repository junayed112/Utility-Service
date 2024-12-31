package com.reddot.utilityservice.enums;

public enum Regex {

    DESCO_BILL_NUMBER("[a-zA-Z_0-9]{1,30}"),
    DESCO_ACCOUNT_NUMBER("[a-zA-Z_0-9]{1,30}"),
    WASA_ACCOUNT_NUMBER("[a-zA-Z_0-9]{1,30}"),
    DESCO_BILL_NO("[a-zA-Z_0-9]{1,30}"),

    NESCO("[a-zA-Z_0-9]{1,30}"),
    MOBILE_NUMBER("^(?:\\+?88|0088)?01[15-9]\\d{8}$"),
    AMOUNT("^\\d*(\\.\\d+)?");

    public final String value;

    Regex(String value) {
        this.value = value;
    }


}

