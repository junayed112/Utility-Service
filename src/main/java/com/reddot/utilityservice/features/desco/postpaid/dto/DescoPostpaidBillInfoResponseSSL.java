package com.reddot.utilityservice.features.desco.postpaid.dto;

import com.reddot.utilityservice.features.desco.common.dto.DescoBillInfoData;
import lombok.Data;

@Data
public class DescoPostpaidBillInfoResponseSSL {
	private String statusTitle;
	private String transactionId;
	private String statusCode;
	private DescoBillInfoData data;
	private String lid;
	private String status;
}

