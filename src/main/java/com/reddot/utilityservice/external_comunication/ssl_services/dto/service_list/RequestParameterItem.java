package com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class RequestParameterItem{

	@SerializedName("sequence")
	private String sequence;

	@SerializedName("min")
	private String min;

	@SerializedName("max")
	private String max;

	@SerializedName("level")
	private String level;

	@SerializedName("name")
	private String name;

	@SerializedName("data_type")
	private String dataType;

	@SerializedName("type")
	private String type;

	@SerializedName("required")
	private String required;

	@SerializedName("options")
	private List<OptionsItem> options;
}