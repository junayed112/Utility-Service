package com.reddot.utilityservice.enums;

public enum Hints {

    ACCOUNT_NO("E.g. xxxxxxxxx","ই.জি. xxxxxxxxx"),
    DESCO_BILL_NO("E.g. xxxxxxxxx","ই.জি. xxxxxxxxx"),
    WASA_ACCOUNT_NO("E.g. xxxxxxxxx","ই.জি. xxxxxxxxx"),
    PHONE_NUMBER("E.g. xxx191222","ই.জি. xxx১৯১২২২"),
    AMOUNT("E.g. 000.00","ই.জি. ০০০.০০"),
    TITAS_INVOICE("E.g. xxxxxxxx","ই.জি. xxxxxxxx"),
    TITAS_CUSTOMER_CODE("E.g. xxxxxxxx","ই.জি. xxxxxxxx");

    public final String en;
    public final String bn;


    Hints(String en, String bn) {
        this.en = en;
        this.bn = bn;
    }

}
