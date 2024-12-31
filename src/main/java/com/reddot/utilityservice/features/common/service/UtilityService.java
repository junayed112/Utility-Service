package com.reddot.utilityservice.features.common.service;

import com.reddot.utilityservice.common.BillInfoResponse;
import com.reddot.utilityservice.common.FeatureItem;
import com.reddot.utilityservice.common.FeatureListResponse;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.common.redis.dao.RedisDao;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list.Data1Item;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list.SSLServiceListResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.gateway.SSLUtilityGateway;
import com.reddot.utilityservice.features.common.FeatureListBuilder;
import com.reddot.utilityservice.features.desco.postpaid.service.DescoPostpaidService;
import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPostpaidDetailBuilder;

import com.reddot.utilityservice.enums.*;
import com.reddot.utilityservice.features.desco.prepaid.dto.DescoPrepaidDetailBuilder;
import com.reddot.utilityservice.features.desco.prepaid.sevice.DescoPrepaidService;
import com.reddot.utilityservice.features.titas.dto.TitasDetailBuilder;
import com.reddot.utilityservice.features.titas.service.TitasService;
import com.reddot.utilityservice.features.wasa.WasaDetailBuilder;
import com.reddot.utilityservice.features.wasa.service.WasaService;
import com.reddot.utilityservice.widgets.UtilityDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
public class UtilityService {
    private final RedisDao redisDao;
    private final SSLUtilityGateway utilityGateway;
    private final DescoPostpaidService descoPostpaidService;
    private final DescoPrepaidService descoPrepaidService;
    private final WasaService wasaService;
    private final TitasService titasService;

    public UtilityService(RedisDao redisDao, SSLUtilityGateway utilityGateway, DescoPostpaidService descoPostpaidService, DescoPrepaidService descoPrepaidService, WasaService wasaService, TitasService titasService) {
        this.redisDao = redisDao;
        this.utilityGateway = utilityGateway;
        this.descoPostpaidService = descoPostpaidService;
        this.descoPrepaidService = descoPrepaidService;
        this.wasaService = wasaService;
        this.titasService = titasService;
    }

    public UtilityDetail getServiceDetail(String featureCode, String localType){
        switch (UtilityCode.fromString(featureCode)){
            case DESCO_PREPAID:
                return new DescoPrepaidDetailBuilder().getDescoPrepaidDetail(localType);
            case DESCO_POSTPAID:
                return new DescoPostpaidDetailBuilder().getDescoPostpaidDetail(localType);
            case WASA:
                return new WasaDetailBuilder().getWasaDetail(localType);
            case TITAS_METERED:
                return new TitasDetailBuilder().getTitasDetail(localType, UtilityCode.TITAS_METERED.code);
            case TITAS_NON_METERED:
                return new TitasDetailBuilder().getTitasDetail(localType, UtilityCode.TITAS_NON_METERED.code);
        }
        throw new ApplicationException(new CustomError("400","Data not found"));
    }

    public BillInfoResponse getBillInfo(BillInfoBody billInfoBody){

        switch (UtilityCode.fromString(billInfoBody.getFeatureCode())){
            case DESCO_PREPAID:
                return descoPrepaidService.getBillInfo(billInfoBody);
            case WASA:
                return wasaService .getBillInfo(billInfoBody);
            case DESCO_POSTPAID:
                return descoPostpaidService.getBillInfo(billInfoBody);
            case TITAS_METERED:
            case TITAS_NON_METERED:
                return titasService.getBillInfo(billInfoBody);
        }
        throw new ApplicationException(new CustomError("400","INVALID_CODE"));
    }

    public FeatureListResponse getFeatureList(String localType){
        return FeatureListBuilder.getFeatureList(localType);
    }

    public CoreTransactionResponse payBill(BillPaymentBody billPaymentBody){

        switch (UtilityCode.fromString(billPaymentBody.getFeatureCode())){
            case DESCO_PREPAID:
                return descoPrepaidService.payBill(billPaymentBody);
            case WASA:
               return wasaService.payBill(billPaymentBody);
            case DESCO_POSTPAID:
                return descoPostpaidService.payBill(billPaymentBody);
            case TITAS_METERED:
            case TITAS_NON_METERED:
                return titasService.payBill(billPaymentBody);
        }
        throw new ApplicationException(new CustomError("400","INVALID_CODE"));
    }

   //@Scheduled(fixedRate = 86400000,initialDelay = 10000) // TODO: need to uncomment while enable ssl service
    public void getAllServices(){
        try {
            SSLServiceListResponse utilityServicesResponse = utilityGateway.getUtilityServices();
            saveDataToRedis(utilityServicesResponse);
           /// return utilityGateway.getUtilityServices();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveDataToRedis(SSLServiceListResponse utilityServicesResponse) {

        try {
            utilityServicesResponse.getData().forEach(
                    category -> {
                        category.getData1().forEach(
                                utilityItem -> {
                                    String utilityCode = utilityItem.getUtilityCode();
                                    utilityItem.getData2().forEach(
                                            utilityDetail -> {
                                                String billType = utilityDetail.getUtilityBillType();
                                                String key = utilityCode + "_" + billType;
                                                redisDao.setObject(key, utilityDetail);
                                            }
                                    );
                                }
                        );
                    }
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Data1Item getServiceByType(String type){

        try {
            if (redisDao.hasKey(type)) {
                return redisDao.getObject(type, Data1Item.class);
            } else {
                throw new ApplicationException(new CustomError("400", "Data not found"));
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException(new CustomError("400", "Data not found"));
        }
    }

}
