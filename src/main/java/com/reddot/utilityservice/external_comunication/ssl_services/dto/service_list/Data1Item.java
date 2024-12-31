package com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Data1Item{

	@SerializedName("utility_value")
	private String utilityValue;

	@SerializedName("utility_code")
	private String utilityCode;

	@SerializedName("utility_image")
	private String utilityImage;

	@SerializedName("data")
	private List<UtilityDetailData> data2;
}