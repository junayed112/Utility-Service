package com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class OptionsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("value")
	private String value;
}