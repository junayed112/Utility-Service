package com.reddot.utilityservice.configuration;

public class RequestCorrelation {

    public static final String CORRELATION_ID = "correlationId";
    public static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-Id";

    private static final ThreadLocal<String> id = new ThreadLocal<String>();


    public static String getId() {
        return id.get();
    }

    public static void setId(String correlationId) {
        id.set(correlationId);
    }
}
