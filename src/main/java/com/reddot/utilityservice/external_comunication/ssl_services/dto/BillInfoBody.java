package com.reddot.utilityservice.external_comunication.ssl_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillInfoBody {
	 HashMap<String,String> dynamicFields;
	 String featureCode;
	 String walletNo;
}


//{
//		"bill_type":"",
//		"transaction_id":"",
//		"dynamic_fields":{
//		"billno":"",
//		"mobile_no":""
//		}
//}
