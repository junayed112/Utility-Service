package com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataItem{

	@SerializedName("category_image")
	private String categoryImage;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("data")
	private List<Data1Item> data1;

	@SerializedName("category_value")
	private String categoryValue;
}