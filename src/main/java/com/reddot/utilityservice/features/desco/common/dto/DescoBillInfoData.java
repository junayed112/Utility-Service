package com.reddot.utilityservice.features.desco.common.dto;

import lombok.Data;

@Data
public class DescoBillInfoData {
	private String accountNumber;
	private String billNumber;
	private String zoneCode;
	private String dueDate;
	private String organizationCode;
	private String tariff;
	private String settlementAccount;
	private String billAmount;
	private String vatAmount;
	private String stampAmount;
	private String lpcAmount;
	private String totalAmount;
	private String customerName;
	private String bankCode;
	private String meterNo;
}