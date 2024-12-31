package com.reddot.utilityservice.enums;



public enum Labels {

    ACCOUNT_NO("Account number","একাউন্ট নাম্বার"),
    BILL_NO("Bill Number","বিল নাম্বার"),
    AMOUNT("Amount", "এমাউন্ট"),
    INVOICE_NUMBER("Invoice Number", "ইনভয়েস নাম্বার"),
    CUSTOMER_CODE("Customer Code", "কাস্টমার কোড"),
    NOTIFICATION_NUMBER("Notification Number","নটিফিকেশন নাম্বার"),
    MOBILE_NO("Mobile number","মোবাইল নাম্বার");

    public final String en;
    public final String bn;


    Labels(String en, String bn) {
        this.en = en;
        this.bn = bn;
    }

}
