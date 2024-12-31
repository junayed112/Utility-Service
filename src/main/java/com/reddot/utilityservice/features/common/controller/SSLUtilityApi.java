package com.reddot.utilityservice.features.common.controller;

import com.reddot.utilityservice.common.BillInfoResponse;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillPaymentBody;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.BillInfoBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "/api/utility",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface SSLUtilityApi {

    @PostMapping(value = "/bill-info")
    BillInfoResponse getBillInfo(@RequestBody BillInfoBody billInfoBody);

    @PostMapping(value = "/bill_payment")
    CoreTransactionResponse payBill(@RequestBody BillPaymentBody billPaymentBody);

}
