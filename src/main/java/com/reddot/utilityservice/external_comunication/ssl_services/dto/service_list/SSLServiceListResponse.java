package com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SSLServiceListResponse {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private String status;
}

