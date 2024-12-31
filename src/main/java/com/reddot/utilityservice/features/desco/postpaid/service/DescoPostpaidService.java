package com.reddot.utilityservice.features.desco.postpaid.service;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.common.BillInfoResponse;
import com.reddot.utilityservice.common.InfoItem;
import com.reddot.utilityservice.common.TransactionEntity;
import com.reddot.utilityservice.common.repository.TransactionRepository;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionRequest;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.core.gateway.CoreGateWay;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.common.redis.dao.RedisDao;
import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPaymenSSLResponse;
import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPostpaidBillInfoResponseSSL;
import com.reddot.utilityservice.features.desco.postpaid.gateway.DescoPostpaidGateway;
import com.reddot.utilityservice.features.desco.postpaid.repository.DescoPostpaidRepository;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Service
public class DescoPostpaidService {

    @Autowired
    RedisDao redisDao;

    private final DescoPostpaidGateway descoPostpaidGateway;
    private final CoreGateWay coreGateWay;
    private final TransactionRepository transactionRepository;
    private final ApplicationPrperties applicationPrperties;

    public DescoPostpaidService(DescoPostpaidGateway descoPostpaidGateway, DescoPostpaidRepository descoPostpaidRepository, CoreGateWay coreGateWay, TransactionRepository transactionRepository, ApplicationPrperties applicationPrperties) {
        this.descoPostpaidGateway = descoPostpaidGateway;
        this.coreGateWay = coreGateWay;
        this.transactionRepository = transactionRepository;
        this.applicationPrperties = applicationPrperties;
    }

    public BillInfoResponse getBillInfo(BillInfoBody billInfoBody){

        try {
            DescoPostpaidBillInfoResponseSSL descoPostpaidBillInfoResponse = descoPostpaidGateway.getDescoPostpaidBillInfo(billInfoBody);

            if (descoPostpaidBillInfoResponse.getStatus() == null || !descoPostpaidBillInfoResponse.getStatus().equals("111")) {
                throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
            }

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setTransactionId(descoPostpaidBillInfoResponse.getTransactionId());
            transactionEntity.setFeatureCode(billInfoBody.getFeatureCode());

            try {
                transactionRepository.save(transactionEntity);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
            }

            BillInfoResponse billInfoResponse = new BillInfoResponse();
            billInfoResponse.setTransaction_id(descoPostpaidBillInfoResponse.getTransactionId());

            ArrayList<InfoItem> list = billInfoResponse.getInfoItemArrayList();
            list.add(new InfoItem("Due Date", descoPostpaidBillInfoResponse.getData().getDueDate()));
            list.add(new InfoItem("Bill Amount", descoPostpaidBillInfoResponse.getData().getBillAmount()));
            list.add(new InfoItem("Vat Amount", descoPostpaidBillInfoResponse.getData().getVatAmount()));
            list.add(new InfoItem("Stamp Amount", descoPostpaidBillInfoResponse.getData().getStampAmount()));
            list.add(new InfoItem("LPC Amount", descoPostpaidBillInfoResponse.getData().getVatAmount()));
            list.add(new InfoItem("Total Amount", descoPostpaidBillInfoResponse.getData().getVatAmount()));
            return billInfoResponse;

        }catch (Exception e){
            throw new ApplicationException(new CustomError("400",applicationPrperties.errorMessage));
        }

    }

    public CoreTransactionResponse payBill(BillPaymentBody billPaymentBody){

        try {

            Optional<TransactionEntity> transactionEntityOptional = transactionRepository.findAllByTransactionId(billPaymentBody.getTransactionId());
            TransactionEntity transactionEntity = null;
            if (transactionEntityOptional != null && transactionEntityOptional.isPresent()) {
                transactionEntity = transactionEntityOptional.get();
                if (transactionEntity.getSslTransactionStatusCode() != null &&
                        transactionEntity.getSslTransactionStatusCode().equals("111")) {
                    throw new ApplicationException(new CustomError("400", "Bill already paid"));
                }
            }

            DescoPaymenSSLResponse descoPaymenSSLResponse = descoPostpaidGateway.payBill(billPaymentBody);
            if (descoPaymenSSLResponse.getStatus_code().equals("111")) {
                CoreTransactionRequest coreTransactionRequest = new CoreTransactionRequest(
                        billPaymentBody.getWalletNo(),
                        "SYSTEM",
                        "BILL_PAYMENT",
                        billPaymentBody.getPin(),
                        billPaymentBody.getAmount()
                );
              CoreTransactionResponse coreTransactionResponse = coreGateWay.perFormTransaction(coreTransactionRequest);

              if(coreTransactionResponse.getStatus()!= null){
//                  transactionEntity.setBillType(billPaymentBody.getBillType());
//                  transactionEntity.setAmount(billPaymentBody.getAmount());
//                  transactionEntity.setNotificationNumber(billPaymentBody.getNotificationNumber());
//                  transactionEntity.setTransactionStatusCode(coreTransactionResponse.getCode());
                  return coreTransactionResponse;
              }
            }
        }catch (Exception e){
            log.debug("payBill: {}",e.getMessage());
            throw new ApplicationException(new CustomError("400",applicationPrperties.errorMessage));
        }
        return null;
    }

}
