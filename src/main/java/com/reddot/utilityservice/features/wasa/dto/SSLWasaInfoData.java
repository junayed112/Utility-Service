package com.reddot.utilityservice.features.wasa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SSLWasaInfoData{
	private String account_number;
	private String bill_number;
	private String bill_amount;
	private String vat_amount;
	private BigDecimal total_amount;
	private String other1_amount;
	private String other2_amount;
	private String zone_code;
}