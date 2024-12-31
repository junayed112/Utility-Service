package com.reddot.utilityservice.external_comunication.core.gateway;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.external_comunication.core.apiclient.CoreApiClient;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionRequest;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.requestmoney.dto.CheckPinDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoreGateWay {

    private final String coreTransactionEndPoint = "/txn";
    private final String checkPinEndPoint = "/account/check/pin";
    private final ApplicationPrperties applicationPrperties;
    private final CoreApiClient coreApiClient;

    public CoreGateWay(ApplicationPrperties applicationPrperties, CoreApiClient coreApiClient) {
        this.applicationPrperties = applicationPrperties;
        this.coreApiClient = coreApiClient;
    }
    public CoreTransactionResponse perFormTransaction(CoreTransactionRequest coreTransactionRequest){
        try {
            String url = new StringBuilder(applicationPrperties.coreBaseUrl)
                    .append(coreTransactionEndPoint)
                    .toString();
            CoreTransactionResponse response = coreApiClient.callPost(url, coreTransactionRequest, CoreTransactionResponse.class);
            // need to check status
            return response;
        }
        catch (ApplicationException e){
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            log.debug("perFormTransaction: {}",e.getMessage());
            throw new ApplicationException(new CustomError("400",e.getMessage()));
        }
    }

    public CheckPinDto checkPin(CheckPinDto checkPinDto){
        try{
            String url = new StringBuilder(applicationPrperties.coreBaseUrl)
                    .append(checkPinEndPoint)
                    .toString();

            return coreApiClient.callPost(url, checkPinDto, CheckPinDto.class);
        }catch (ApplicationException exception){
            throw exception;
        }catch(Exception exception){
            throw new ApplicationException(new CustomError("400", exception.getMessage()));
        }
    }
}
