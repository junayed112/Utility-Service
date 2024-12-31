package com.reddot.utilityservice.features.wasa.dto;

import com.reddot.utilityservice.base.CommonResponse;
import com.reddot.utilityservice.base.CommonSslRequestBody;
import com.reddot.utilityservice.base.SSLBaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SSLWasaInfoResponse extends SSLBaseResponse {
    SSLWasaInfoData data;
}
