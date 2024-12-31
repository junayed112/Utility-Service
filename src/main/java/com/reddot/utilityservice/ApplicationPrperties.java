package com.reddot.utilityservice;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApplicationPrperties {

    @Value("${ssl.baseUrl}")
    public String SSL_BASE_URL;

    @Value("${ssl.bill_info}")
    public String BILL_INFO;

    @Value("${ssl.bill-payment}")
    public String BILL_PAYMENT;


    @Value("${error.common_error_message}")
    public String errorMessage;

    @Value("${core.base_url}")
    public String coreBaseUrl;


    //Notification
    @Value("${sms.gateway.auth.token}")
    public String smsAuthToken;

    @Value("${sms.gateway.url}")
    public String smsBaseUrl;

    @Value("${page.size}")
    public Integer pageSize;

    @Value("${validation.secret.key}")
    public String secretKey;







}
