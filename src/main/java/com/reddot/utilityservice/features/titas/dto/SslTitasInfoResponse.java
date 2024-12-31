package com.reddot.utilityservice.features.titas.dto;

import com.reddot.utilityservice.base.SSLBaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SslTitasInfoResponse extends SSLBaseResponse {
    private SslTitasInfoData data;
}
