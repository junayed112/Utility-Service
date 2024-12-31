package com.reddot.utilityservice.features.desco.common.dto;

import com.reddot.utilityservice.base.SSLBaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SslDescoInfoResponse extends SSLBaseResponse {
    private DescoBillInfoData data;
}
