package com.reddot.utilityservice.features.desco.postpaid.service;

import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPostpaidBillInfoResponseSSL;
import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPostpaidEntity;
import org.springframework.beans.BeanUtils;

public class DescoEntityMaper {
    public static void mapData(DescoPostpaidBillInfoResponseSSL descoPostpaidBillInfoResponse, DescoPostpaidEntity descoPostpaidEntity) {
        BeanUtils.copyProperties(descoPostpaidBillInfoResponse.getData(),descoPostpaidEntity);
        descoPostpaidEntity.setStatus(descoPostpaidBillInfoResponse.getStatusTitle());
        descoPostpaidEntity.setStatus_code(descoPostpaidBillInfoResponse.getStatusCode());
        descoPostpaidEntity.setLid(descoPostpaidBillInfoResponse.getLid());
        descoPostpaidEntity.setStatus(descoPostpaidBillInfoResponse.getStatus());
    }
}
