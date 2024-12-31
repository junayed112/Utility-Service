package com.reddot.utilityservice.features.wasa.service;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.base.CommonSslRequestBody;
import com.reddot.utilityservice.common.BillInfoResponse;
import com.reddot.utilityservice.common.Constants;
import com.reddot.utilityservice.common.InfoItem;
import com.reddot.utilityservice.common.TransactionEntity;
import com.reddot.utilityservice.common.redis.dao.RedisDao;
import com.reddot.utilityservice.common.repository.TransactionRepository;
import com.reddot.utilityservice.enums.UtilityTitle;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.features.common.service.PaymentService;
import com.reddot.utilityservice.features.wasa.dto.SSLWasaInfoResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list.UtilityDetailData;
import com.reddot.utilityservice.external_comunication.ssl_services.gateway.SSLUtilityGateway;
import com.reddot.utilityservice.features.wasa.dto.WasaInfoEnum;
import com.reddot.utilityservice.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


@Slf4j
@Service
public class WasaService {

    @Autowired
    RedisDao redisDao;

    private final SSLUtilityGateway sslUtilityGateway;

    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final ApplicationPrperties applicationPrperties;

    public WasaService(SSLUtilityGateway sslUtilityGateway, TransactionRepository transactionRepository,
                       PaymentService paymentService, ApplicationPrperties applicationPrperties) {
        this.sslUtilityGateway = sslUtilityGateway;
        this.paymentService = paymentService;
        this.transactionRepository = transactionRepository;
        this.applicationPrperties = applicationPrperties;
    }

    public BillInfoResponse getBillInfo(BillInfoBody billInfoBody) {

        //TODO: same transaction check withing certaintime
        try {
            UtilityDetailData utilityDetailData = null;
            if (redisDao.hasKey(billInfoBody.getFeatureCode())) {
                utilityDetailData = redisDao.getObject(billInfoBody.getFeatureCode(), UtilityDetailData.class);
            } else {
                throw new ApplicationException(new CustomError("400", "Data not found"));
                //may be call again to fetch service list
            }

            String transactionId = Util.getTransactionId(billInfoBody.getWalletNo());

            HashMap<String, String> map = CommonSslRequestBody.getCommonBody(utilityDetailData.getUtilityAuthKey(),
                    utilityDetailData.getUtilitySecretKey(), utilityDetailData.getUtilityBillType(), transactionId);
            map.putAll(billInfoBody.getDynamicFields());
            SSLWasaInfoResponse sslWasaInfoResponse = sslUtilityGateway.getBillInfo(map, SSLWasaInfoResponse.class);

            if (sslWasaInfoResponse.getStatus() != null && sslWasaInfoResponse.getStatus_code().equals("000")) {

                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setTransactionId(sslWasaInfoResponse.getTransaction_id());
                transactionEntity.setFeatureCode(billInfoBody.getFeatureCode());
                transactionEntity.setBillType(utilityDetailData.getUtilityBillType());
                transactionEntity.setUtilityAuthKey(utilityDetailData.getUtilityAuthKey());
                transactionEntity.setUtilitySecreteKey(utilityDetailData.getUtilitySecretKey());
                transactionEntity.setAmount(sslWasaInfoResponse.getData().getTotal_amount());

                try {
                    transactionRepository.save(transactionEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
                }

                BillInfoResponse billInfoResponse = new BillInfoResponse();
                billInfoResponse.setTransaction_id(sslWasaInfoResponse.getTransaction_id());
                billInfoResponse.setDue_amount(sslWasaInfoResponse.getData().getTotal_amount().toString());
                ArrayList<InfoItem> list = billInfoResponse.getInfoItemArrayList();
                list.add(new InfoItem("Bill amount", Constants.taka+" "+sslWasaInfoResponse.getData().getBill_amount()));
                list.add(new InfoItem("Vat amount", Constants.taka+" "+sslWasaInfoResponse.getData().getVat_amount()));
                list.add(new InfoItem("Other amount", Constants.taka+" "+sslWasaInfoResponse.getData().getOther1_amount()));
                list.add(new InfoItem("Total amount", Constants.taka+" "+sslWasaInfoResponse.getData().getTotal_amount().toString()));
                billInfoResponse.setInfoItemArrayList(list);
                return billInfoResponse;
            }
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
        }
        throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
    }

    public CoreTransactionResponse payBill(BillPaymentBody billPaymentBody) {
        try{
            return paymentService.payBill(billPaymentBody, WasaInfoEnum.MERCHANT_NUMBER.value, WasaInfoEnum.TRANSACTION_TYPE_WASA_CORE.value, UtilityTitle.WASA);
        }
        catch(ApplicationException applicationException){
            throw applicationException;
        }
        catch(Exception exception){
            throw new ApplicationException(new CustomError("400", exception.getMessage()));
        }
    }
}


//{
//        "status": "PROCESSED",
//        "message": "Txn Successful",
//        "txnId": "E078D1236653",
//        "amount": 40.00,
//        "fee": -1,
//        "commission": -1,
//        "balanceFrom": -1,
//        "balanceTo": -1,
//        "ticket": "N/A",
//        "traceNo": "N/A",
//        "fromAccount": "018331840840",
//        "toAccount": "018113334445",
//        "txnTime": "2023-02-24T09:06:52.359+0000",
//        "txnType": "UTILITY_BILL_PAYMENT_WASA_SSL",
//        "txnTypeName": "Wasa Bill Payment Via SSL"
//        }
