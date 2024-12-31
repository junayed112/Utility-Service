package com.reddot.utilityservice.enums;

public enum Keys {

    ACCOUNT_NO("account_no"),
    BILL_NO("billno"),
    MOBILE_NO("mobile_no"),
    AMOUNT("amount"),
    INVOICE_NO("invoiceNo"),
    CUSTOMER_CODE("customerCode"),
    NOTIFICATION_NUMBER("notification_no");

    public final String key;

    Keys(String key) {
        this.key = key;
    }

}
