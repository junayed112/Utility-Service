package com.reddot.utilityservice.features.desco.postpaid.dto;

import com.reddot.utilityservice.base.SSLBaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescoPaymenSSLResponse extends SSLBaseResponse {
    DescoPostpaidPayResponseData data;
}
