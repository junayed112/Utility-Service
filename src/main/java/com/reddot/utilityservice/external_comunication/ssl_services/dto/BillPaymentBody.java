package com.reddot.utilityservice.external_comunication.ssl_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillPaymentBody {
	 HashMap<String,String> dynamicFields;
	 String featureCode;
	 String transactionId;
	String walletNo;
	String pin;
	BigDecimal amount;
	private String notificationNumber;
}


//{
//		"billType":"",
//		"walletNo":"",
//		"dynamic_fields":{
//		"transaction_id":""
//		}
//}

