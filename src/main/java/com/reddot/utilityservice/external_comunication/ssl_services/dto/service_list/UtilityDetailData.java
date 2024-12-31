package com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UtilityDetailData {

	@SerializedName("bill_info")
	private BillInfo billInfo;

	@SerializedName("amount_type")
	private Object amountType;

	@SerializedName("utility_bill_type")
	private String utilityBillType;

	@SerializedName("utility_secret_key")
	private String utilitySecretKey;

	@SerializedName("is_partial_allowed")
	private boolean isPartialAllowed;

	@SerializedName("utility_auth_key")
	private String utilityAuthKey;

	@SerializedName("beneficiary_add_option")
	private String beneficiaryAddOption;

	@SerializedName("recurring_options")
	private String recurringOptions;

	@SerializedName("bill_payment")
	private BillPayment billPayment;

	@SerializedName("biller_status")
	private String billerStatus;

	@SerializedName("one_time_payment")
	private String oneTimePayment;
}