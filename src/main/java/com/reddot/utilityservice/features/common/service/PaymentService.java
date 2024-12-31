package com.reddot.utilityservice.features.common.service;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.base.CommonSslRequestBody;
import com.reddot.utilityservice.base.SSLBaseResponse;
import com.reddot.utilityservice.common.TransactionEntity;
import com.reddot.utilityservice.common.repository.TransactionRepository;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.enums.UtilityTitle;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionRequest;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.core.gateway.CoreGateWay;
import com.reddot.utilityservice.external_comunication.sms.SmsGateway;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.external_comunication.ssl_services.gateway.SSLUtilityGateway;
import com.reddot.utilityservice.features.titas.dto.TitasInfoEnum;
import com.reddot.utilityservice.utils.Util;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class PaymentService {

    private final CoreGateWay coreGateWay;
    private final TransactionRepository transactionRepository;
    private final SSLUtilityGateway sslUtilityGateway;
    private final ApplicationPrperties applicationPrperties;
    private final SmsGateway smsGateway;
    public PaymentService(CoreGateWay coreGateWay, TransactionRepository transactionRepository, SSLUtilityGateway sslUtilityGateway, ApplicationPrperties applicationPrperties, SmsGateway smsGateway) {
        this.coreGateWay = coreGateWay;
        this.transactionRepository = transactionRepository;
        this.sslUtilityGateway = sslUtilityGateway;
        this.applicationPrperties = applicationPrperties;
        this.smsGateway = smsGateway;
    }

    public CoreTransactionResponse payBill(BillPaymentBody billPaymentBody, String marchantNumber, String transactionType, UtilityTitle utilityTitle){
        try{
            Optional<TransactionEntity> transactionEntityOptional = transactionRepository.findAllByTransactionId(billPaymentBody.getTransactionId());
            TransactionEntity transactionEntity = null;

            if(transactionEntityOptional != null && transactionEntityOptional.isPresent()){
                transactionEntity = transactionEntityOptional.get();
                if(transactionEntity.getSslTransactionStatus() != null && transactionEntity.getSslTransactionStatusCode().equals("111")){
                    throw new ApplicationException(new CustomError("400", "Bill already paid"));
                }
                //need to check if transaction is in a certain time.
                CoreTransactionRequest coreTransactionRequest = new CoreTransactionRequest(billPaymentBody.getWalletNo(), marchantNumber, transactionType, billPaymentBody.getPin(), transactionEntity.getAmount());
                try{
                    CoreTransactionResponse coreTransactionResponse = coreGateWay.perFormTransaction(coreTransactionRequest);
                    if(coreTransactionResponse.getStatus() != null && coreTransactionResponse.getStatus().equalsIgnoreCase("PROCESSED")){
                        Util.setTransactionEntityProperties(transactionEntity, billPaymentBody.getAmount(), billPaymentBody.getNotificationNumber(), coreTransactionResponse.getStatus(), coreTransactionResponse.getCode(), coreTransactionResponse.getCommission(), coreTransactionResponse.getFee(), coreTransactionResponse.getTxnId(), billPaymentBody.getWalletNo());
                        try{
                            transactionRepository.save(transactionEntity);
                        }catch (Exception exception){
                            throw new ApplicationException(new CustomError("400", "Can't update transaction"));
                        }

                        HashMap<String, String> map = CommonSslRequestBody.getCommonBody(transactionEntity.getUtilityAuthKey(), transactionEntity.getUtilitySecreteKey(), transactionEntity.getBillType(), transactionEntity.getTransactionId());
                        map.put("paymentId", TitasInfoEnum.SSL_PAYMENT_ID.value);

                        SSLBaseResponse sslBaseResponse = sslUtilityGateway.payBill(map, SSLBaseResponse.class);
                        if(sslBaseResponse.getStatus_code() != null && sslBaseResponse.getStatus_code().equals("111")){
                            transactionEntity.setSslTransactionStatus(sslBaseResponse.getStatus());
                            transactionEntity.setSslTransactionStatusCode(sslBaseResponse.getStatus_code());
                            try{
                                transactionRepository.save(transactionEntity);
                                smsGateway.sendUtilitySMS(utilityTitle.en, billPaymentBody.getNotificationNumber(), coreTransactionResponse);
                                coreTransactionResponse.setMessage(new StringBuilder("Payment of Tk ").append(coreTransactionResponse.getAmount()).append(" made from ").append(billPaymentBody.getWalletNo()).append(" is successful.").toString());
                                return coreTransactionResponse;
                            }catch (Exception exception){
                                throw new ApplicationException(new CustomError("400", "Can't update transaction"));
                            }
                        }else{
                            throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
                        }
                    }
                    else{
                        throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
                    }
                }
                catch(ApplicationException applicationException){
                    throw applicationException;
                }
            }
            else{
                throw new ApplicationException(new CustomError("400", "Transaction not found."));
            }
        }
        catch(ApplicationException applicationException){
            throw new ApplicationException(new CustomError("400", applicationException.getCustomError().getMessage()));
        }
    }
}
