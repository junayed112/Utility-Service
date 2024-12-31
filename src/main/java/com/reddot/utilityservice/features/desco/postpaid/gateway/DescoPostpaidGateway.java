package com.reddot.utilityservice.features.desco.postpaid.gateway;


import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.external_comunication.ssl_services.apiclient.SSLApiClient;
import com.reddot.utilityservice.base.CommonSslRequestBody;
import com.reddot.utilityservice.enums.UtilityKeys;
import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPaymenSSLResponse;
import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPostpaidBillInfoResponseSSL;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;


@Service
@Slf4j
public class DescoPostpaidGateway {


    private final WebClient webClient;
    private final SSLApiClient apiClient;
    private final ApplicationPrperties applicationPrperties;

    public DescoPostpaidGateway(WebClient webClient, SSLApiClient apiClient, ApplicationPrperties applicationPrperties) {
        this.webClient = webClient;
        this.apiClient = apiClient;
        this.applicationPrperties = applicationPrperties;
    }

    public DescoPostpaidBillInfoResponseSSL getDescoPostpaidBillInfo(BillInfoBody billInfoBody) {

        String transactionId = Util.updateMsisdn(billInfoBody.getWalletNo()).substring(2) + System.currentTimeMillis() + "0";

        if (transactionId.length() > 25) {
            transactionId = transactionId.substring(0, 25);
        }

        HashMap<String, String> map = CommonSslRequestBody.getCommonBody(UtilityKeys.DESCO_POSTPAID.auth_key, UtilityKeys.DESCO_POSTPAID.secrete_key,"","");
        map.put("transaction_id", transactionId);
        map.putAll(billInfoBody.getDynamicFields());

        String url = new StringBuilder(applicationPrperties.SSL_BASE_URL)
                .append(applicationPrperties.BILL_INFO)
                .toString();
        DescoPostpaidBillInfoResponseSSL response = apiClient.callPost(url, map, DescoPostpaidBillInfoResponseSSL.class,CommonSslRequestBody.getCommonHeader());
        return response;
    }

    public DescoPaymenSSLResponse payBill(BillPaymentBody billPaymentBody) {

        HashMap<String, String> map = CommonSslRequestBody.getCommonBody(UtilityKeys.DESCO_POSTPAID.auth_key, UtilityKeys.DESCO_POSTPAID.secrete_key,"","");
        map.put("bill_type", "POSTPAID");
        map.put("transaction_id",billPaymentBody.getTransactionId());
        map.putAll(billPaymentBody.getDynamicFields());

        String url = new StringBuilder(applicationPrperties.SSL_BASE_URL)
                .append(applicationPrperties.BILL_INFO)
                .toString();
        DescoPaymenSSLResponse response = apiClient.callPost(url, map, DescoPaymenSSLResponse.class,CommonSslRequestBody.getCommonHeader());
        return response;
    }


//    public void getDescoPrepaidBillInfo(BillInfoBody descoBillInfoBody){
//        HashMap<String,String> map = CommonSslRequestBody.getCommonBody(UtilityKeys.DESCO_PREPAID.auth_key, UtilityKeys.DESCO_PREPAID.secrete_key);
//        map.putAll(descoBillInfoBody.getDynamicFields());
//        map.put("transaction_id","64646");
//        String url = new StringBuilder(applicationPrperties.SSL_BASE_URL)
//                .append(applicationPrperties.BILL_INFO)
//                .toString();
//        String response =  apiClient.callPost(url,map,String.class);
//    }
}
