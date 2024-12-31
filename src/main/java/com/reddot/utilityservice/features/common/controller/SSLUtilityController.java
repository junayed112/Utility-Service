package com.reddot.utilityservice.features.common.controller;


import com.reddot.utilityservice.common.BillInfoResponse;
import com.reddot.utilityservice.common.FeatureItem;
import com.reddot.utilityservice.common.FeatureListResponse;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.features.common.service.UtilityService;
import com.reddot.utilityservice.widgets.UtilityDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/utility", produces = MediaType.APPLICATION_JSON_VALUE)
public class SSLUtilityController implements SSLUtilityApi{

    private final UtilityService utilityService;
    public SSLUtilityController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @GetMapping("/detail")
    public UtilityDetail getAllServices(@RequestParam("utilityCode") String featureCode, @RequestHeader("language") String language) { //TODO: need to rename the DataItem classes
        return utilityService.getServiceDetail(featureCode,language);
    }

    @GetMapping("/feature-list")
    public FeatureListResponse getFeatureList(@RequestHeader("language") String localType) {
        return utilityService.getFeatureList(localType);
    }

    @Override
    public BillInfoResponse getBillInfo(BillInfoBody billInfoBody) {
       return utilityService.getBillInfo(billInfoBody);
    }
    @Override
    public CoreTransactionResponse payBill(BillPaymentBody billPaymentBody) {
        return utilityService.payBill(billPaymentBody);
    }

}


