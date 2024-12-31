package com.reddot.utilityservice.enums;



public enum FieldType {

    TEXT_FIELD("text"),
    NOTIFICATION_WIDGET("notification_widget"),
    DROP_DOWN("dropdown");

    public final String type;


    FieldType(String type) {
        this.type = type;

    }

}


