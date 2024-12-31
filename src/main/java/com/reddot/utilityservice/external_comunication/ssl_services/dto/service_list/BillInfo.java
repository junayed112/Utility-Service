package com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BillInfo{

	@SerializedName("action_url")
	private String actionUrl;

	@SerializedName("request_parameter")
	private List<RequestParameterItem> requestParameter;
}
