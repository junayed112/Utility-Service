package com.reddot.utilityservice.features.titas.service;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.base.CommonSslRequestBody;
import com.reddot.utilityservice.common.BillInfoResponse;
import com.reddot.utilityservice.common.InfoItem;
import com.reddot.utilityservice.common.TransactionEntity;
import com.reddot.utilityservice.common.redis.dao.RedisDao;
import com.reddot.utilityservice.common.repository.TransactionRepository;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.enums.UtilityCode;
import com.reddot.utilityservice.enums.UtilityTitle;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list.UtilityDetailData;
import com.reddot.utilityservice.external_comunication.ssl_services.gateway.SSLUtilityGateway;
import com.reddot.utilityservice.features.common.service.PaymentService;
import com.reddot.utilityservice.features.titas.dto.SslTitasInfoResponse;
import com.reddot.utilityservice.features.titas.dto.TitasInfoEnum;
import com.reddot.utilityservice.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class TitasService {

    private final RedisDao redisDao;
    private final TransactionRepository transactionRepository;
    private final ApplicationPrperties applicationPrperties;
    private final SSLUtilityGateway sslUtilityGateway;
    private final PaymentService paymentService;

    public TitasService(RedisDao redisDao, TransactionRepository transactionRepository, ApplicationPrperties applicationPrperties, SSLUtilityGateway sslUtilityGateway, PaymentService paymentService) {
        this.redisDao = redisDao;
        this.transactionRepository = transactionRepository;
        this.applicationPrperties = applicationPrperties;
        this.sslUtilityGateway = sslUtilityGateway;
        this.paymentService = paymentService;
    }

    public BillInfoResponse getBillInfo(BillInfoBody billInfoBody){
        try{
            UtilityDetailData utilityDetailData = null;
            String featureCode = billInfoBody.getFeatureCode();
            if(redisDao.hasKey(featureCode)){
                utilityDetailData = redisDao.getObject(billInfoBody.getFeatureCode(), UtilityDetailData.class);
            }else{
                throw new ApplicationException(new CustomError("400", "Data not found"));
                //have to implement what will actually happen if data not found.
            }
            String transactionId = Util.getTransactionId(billInfoBody.getWalletNo());

            HashMap<String, String> map = CommonSslRequestBody.getCommonBody(utilityDetailData.getUtilityAuthKey(), utilityDetailData.getUtilitySecretKey(), utilityDetailData.getUtilityBillType(), transactionId);
            map.putAll(billInfoBody.getDynamicFields());

            SslTitasInfoResponse sslTitasInfoResponse = sslUtilityGateway.getBillInfo(map, SslTitasInfoResponse.class);
            if(sslTitasInfoResponse.getStatus() != null && sslTitasInfoResponse.getStatus_code().equals("000")){
                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setTransactionId(sslTitasInfoResponse.getTransaction_id());
                transactionEntity.setFeatureCode(featureCode);
                transactionEntity.setBillType(utilityDetailData.getUtilityBillType());
                transactionEntity.setUtilityAuthKey(utilityDetailData.getUtilityAuthKey());
                transactionEntity.setUtilitySecreteKey(utilityDetailData.getUtilitySecretKey());
                transactionEntity.setAmount(sslTitasInfoResponse.getData().getTotalAmount());

                try{
                    transactionRepository.save(transactionEntity);
                } catch(Exception exception){
                    throw new ApplicationException(new CustomError("400", "Can't save transaction"));
                }

                BillInfoResponse billInfoResponse = new BillInfoResponse();
                billInfoResponse.setTransaction_id(sslTitasInfoResponse.getTransaction_id());
                ArrayList<InfoItem> infoItems = billInfoResponse.getInfoItemArrayList();

                if(featureCode.equals(UtilityCode.TITAS_METERED.code)){
                    //add titas metered response body

                }else if(featureCode.equals(UtilityCode.TITAS_NON_METERED.code)){
                    infoItems.add(new InfoItem("Customer Code", sslTitasInfoResponse.getData().getCustomerCode()));
                    infoItems.add(new InfoItem("Customer Name", sslTitasInfoResponse.getData().getCustomerName()));
                    infoItems.add(new InfoItem("Phone Number", sslTitasInfoResponse.getData().getPhoneNumber()));
                    infoItems.add(new InfoItem("Zone Code", sslTitasInfoResponse.getData().getZoneCode()));
                    infoItems.add(new InfoItem("Bill Type", sslTitasInfoResponse.getData().getBillType()));
                    infoItems.add(new InfoItem("Bill Amount", String.valueOf(sslTitasInfoResponse.getData().getBillAmount())));
                    infoItems.add(new InfoItem("Stamp Amount", String.valueOf(sslTitasInfoResponse.getData().getStampAmount())));
                    infoItems.add(new InfoItem("Total Amount", String.valueOf(sslTitasInfoResponse.getData().getTotalAmount())));
                }
                billInfoResponse.setInfoItemArrayList(infoItems);
                return billInfoResponse;
            }
            else {
                throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage)); //should common message while unexpected exception occured
            }
        }
        catch(Exception exception){
            throw new ApplicationException(new CustomError("400", exception.getMessage()));
        }
    }

    public CoreTransactionResponse payBill(BillPaymentBody billPaymentBody){
        try{
            return paymentService.payBill(billPaymentBody, TitasInfoEnum.MERCHANT_NUMBER.value, TitasInfoEnum.TRANSACTION_TYPE_TITAS.value, UtilityTitle.TITAS);
        }
        catch(Exception exception){
            throw new ApplicationException(new CustomError("400", exception.getMessage()));
        }
    }
}
