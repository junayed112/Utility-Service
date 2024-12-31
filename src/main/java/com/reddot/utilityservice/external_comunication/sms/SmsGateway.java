package com.reddot.utilityservice.external_comunication.sms;


import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.apiclient.SSLApiClient;
import com.reddot.utilityservice.features.common.UtilityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;

import static com.reddot.utilityservice.features.common.UtilityUtil.walletNoToMsisdn;


@Service
@Slf4j
public class SmsGateway {

    private final SSLApiClient apiClient;
    private final ApplicationPrperties applicationPrperties;

    public SmsGateway(SSLApiClient apiClient, ApplicationPrperties applicationPrperties) {
        this.apiClient = apiClient;
        this.applicationPrperties = applicationPrperties;
    }

    public void sendUtilitySMS(String type, String notificationNumber, CoreTransactionResponse coreTransactionResponse) {
        try {
            String text = UtilityUtil.getSMSTextUser(type, coreTransactionResponse);
            String urlUser = new StringBuilder(applicationPrperties.smsBaseUrl)
                    .append("/send_sms?msisdn=").append(walletNoToMsisdn(coreTransactionResponse.getFromAccount()))
                    .append("&text=").append(text)
                    .append("&telcoId=").append(1)
                    .toString();
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.add("API_AUTH_CODE", applicationPrperties.smsAuthToken);
            apiClient.callGet(urlUser, header);

            if (notificationNumber != null && !notificationNumber.equals("")) {
                String notificationMsisdn = UtilityUtil.checkNumber(notificationNumber);
                String urlNotification = new StringBuilder(applicationPrperties.smsBaseUrl)
                        .append("/send_sms?msisdn=").append(notificationMsisdn)
                        .append("&text=").append(text)
                        .append("&telcoId=").append(1)
                        .toString();
                apiClient.callGet(urlNotification, header);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendRequestMoneySMS(String requestFrom, String requestTo, BigDecimal requestedAmount){
        try{
            String text = UtilityUtil.getRequestMoneySMS(requestTo, requestedAmount);
            String urlUser = new StringBuilder(applicationPrperties.smsBaseUrl)
                    .append("/send_sms?msisdn=").append(walletNoToMsisdn(requestFrom))
                    .append("&text=").append(text)
                    .append("&telcoId=").append(1)
                    .toString();
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.add("API_AUTH_CODE", applicationPrperties.smsAuthToken);
            apiClient.callGet(urlUser, header);
        }catch(Exception exception){

        }
    }
}

