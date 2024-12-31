package com.reddot.utilityservice.external_comunication.ssl_services.enums;

public enum SSLApiStatus {

    API_SUCCESS("api_success"),
    ERROR("error");

    public final String status;

    SSLApiStatus(String status) {
        this.status = status;

    }
}
