package com.reddot.utilityservice.external_comunication.ssl_services.gateway;


import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.base.CommonSslRequestBody;
import com.reddot.utilityservice.base.SSLBaseResponse;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list.SSLServiceListResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.enums.SSLApiStatus;
import com.reddot.utilityservice.external_comunication.ssl_services.apiclient.SSLApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
@Slf4j
public class SSLUtilityGateway {


    @Value("${ssl.baseUrl}")
    private String SSL_BASE_URL;

    @Value("${ssl.serviceList}")
    private String END_POINT_SERVICE_LIST;


    private final WebClient webClient;
    private final SSLApiClient apiClient;
    private final ApplicationPrperties applicationPrperties;

    public SSLUtilityGateway(WebClient webClient, SSLApiClient apiClient, ApplicationPrperties applicationPrperties) {
        this.webClient = webClient;
        this.apiClient = apiClient;
        this.applicationPrperties = applicationPrperties;
    }

    public SSLServiceListResponse getUtilityServices(){

        String url = new StringBuilder(SSL_BASE_URL)
                .append(END_POINT_SERVICE_LIST)
                .toString();
        return apiClient.callGet(url,SSLServiceListResponse.class, CommonSslRequestBody.getCommonHeader());
    }

    public <T> T getBillInfo(Map<String,String> map,Class<T> responseType) {

        String url = new StringBuilder(applicationPrperties.SSL_BASE_URL)
                .append(applicationPrperties.BILL_INFO)
                .toString();

        T response = apiClient.callPost(url, map, responseType, CommonSslRequestBody.getCommonHeader());

        try {
            SSLBaseResponse sslBaseResponse = (SSLBaseResponse) response;
            if (sslBaseResponse.getStatus().equalsIgnoreCase(SSLApiStatus.ERROR.status)){
                throw new ApplicationException(new CustomError("400",sslBaseResponse.getMessage()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

    public <T>T payBill(Map<String, String> map,Class<T> responseType) {

        String url = new StringBuilder(applicationPrperties.SSL_BASE_URL)
                .append(applicationPrperties.BILL_PAYMENT)
                .toString();

        T response = apiClient.callPost(url, map, responseType, CommonSslRequestBody.getCommonHeader());

        try {
            SSLBaseResponse sslBaseResponse = (SSLBaseResponse) response;
            if (sslBaseResponse.getStatus().equalsIgnoreCase(SSLApiStatus.ERROR.status)){
                throw new ApplicationException(new CustomError("400",sslBaseResponse.getMessage()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return response;

    }


}

