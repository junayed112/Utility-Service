package com.reddot.utilityservice.features.desco.prepaid.sevice;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.base.CommonSslRequestBody;
import com.reddot.utilityservice.common.BillInfoResponse;
import com.reddot.utilityservice.common.InfoItem;
import com.reddot.utilityservice.common.TransactionEntity;
import com.reddot.utilityservice.common.redis.dao.RedisDao;
import com.reddot.utilityservice.common.repository.TransactionRepository;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.enums.UtilityTitle;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list.UtilityDetailData;
import com.reddot.utilityservice.external_comunication.ssl_services.gateway.SSLUtilityGateway;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import com.reddot.utilityservice.features.common.service.PaymentService;
import com.reddot.utilityservice.features.desco.common.dto.DescoInfoEnum;
import com.reddot.utilityservice.features.desco.common.dto.SslDescoInfoResponse;
import com.reddot.utilityservice.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Service
public class DescoPrepaidService {
    private final RedisDao redisDao;
    private final SSLUtilityGateway sslUtilityGateway;
    private final TransactionRepository transactionRepository;
    private final ApplicationPrperties applicationPrperties;
    private final PaymentService paymentService;

    public DescoPrepaidService(RedisDao redisDao, SSLUtilityGateway sslUtilityGateway, TransactionRepository transactionRepository, ApplicationPrperties applicationPrperties, PaymentService paymentService) {
        this.redisDao = redisDao;
        this.sslUtilityGateway = sslUtilityGateway;
        this.transactionRepository = transactionRepository;
        this.applicationPrperties = applicationPrperties;
        this.paymentService = paymentService;
    }

    public BillInfoResponse getBillInfo(BillInfoBody billInfoBody){
        try {
            UtilityDetailData utilityDetailData = null;
            if(redisDao.hasKey(billInfoBody.getFeatureCode())){
                utilityDetailData =  redisDao.getObject(billInfoBody.getFeatureCode(), UtilityDetailData.class);
            }else{
                throw new ApplicationException(new CustomError("400","Data not found"));
                //may be call again to fetch service list
            }

            String transactionId = Util.getTransactionId(billInfoBody.getWalletNo());

            HashMap<String, String> map = CommonSslRequestBody.getCommonBody(utilityDetailData.getUtilityAuthKey(),
                    utilityDetailData.getUtilitySecretKey(),utilityDetailData.getUtilityBillType(),transactionId);
            map.putAll(billInfoBody.getDynamicFields());
            SslDescoInfoResponse sslDescoPreInfoResponse = sslUtilityGateway.getBillInfo(map, SslDescoInfoResponse.class);

            if (sslDescoPreInfoResponse.getStatus() != null && sslDescoPreInfoResponse.getStatus_code().equals("000")) {

                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setTransactionId(sslDescoPreInfoResponse.getTransaction_id());
                transactionEntity.setFeatureCode(billInfoBody.getFeatureCode());
                transactionEntity.setBillType(utilityDetailData.getUtilityBillType());
                transactionEntity.setUtilityAuthKey(utilityDetailData.getUtilityAuthKey());
                transactionEntity.setUtilitySecreteKey(utilityDetailData.getUtilitySecretKey());
                //transactionEntity.setAmount(sslWasaInfoResponse.getData().getTotal_amount());

                try {
                    transactionRepository.save(transactionEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
                }

                BillInfoResponse billInfoResponse = new BillInfoResponse();
                billInfoResponse.setTransaction_id(sslDescoPreInfoResponse.getTransaction_id());
                ArrayList<InfoItem> list = billInfoResponse.getInfoItemArrayList();

                //TODO: need to update list
//                list.add(new InfoItem("Bill amount", sslWasaInfoResponse.getData().getBill_amount()));
//                list.add(new InfoItem("Vat amount", sslWasaInfoResponse.getData().getVat_amount()));
//                list.add(new InfoItem("Other amount", sslWasaInfoResponse.getData().getOther1_amount()));
//                list.add(new InfoItem("Total amount", sslWasaInfoResponse.getData().getTotal_amount().toString()));
                billInfoResponse.setInfoItemArrayList(list);

                return billInfoResponse;
            }

        }catch (Exception e){
            throw new ApplicationException(new CustomError("400",applicationPrperties.errorMessage));
        }
        throw new ApplicationException(new CustomError("400",applicationPrperties.errorMessage));

    }

    public CoreTransactionResponse payBill(BillPaymentBody billPaymentBody){
        try{
            return paymentService.payBill(billPaymentBody, DescoInfoEnum.MERCHANT_NUMBER.value, DescoInfoEnum.TRANSACTION_TYPE_DESCO.value, UtilityTitle.DESCO_PREPAID);
        }catch(ApplicationException exception){
            throw exception;
        }
    }
}
