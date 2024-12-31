package com.reddot.utilityservice.base;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

public class CommonSslRequestBody {

    public static HashMap<String,String> getCommonBody(String authKey, String secreteKy,String billType,String transaction_id) {
        HashMap<String, String> map = new HashMap();
        map.put("utility_auth_key",authKey);
        map.put("utility_secret_key",secreteKy);
        map.put("bill_type",billType);
        map.put("transaction_id",transaction_id);
        return map;
    }


    public static MultiValueMap<String,String> getCommonHeader() {
        MultiValueMap<String, String> map =  new LinkedMultiValueMap<>();
        map.add("AUTH-KEY","40WiKUZe3cH826NPsypgfuKJvKSZBdkX");
        map.add("STK-CODE","FSIBL");
        return map;
    }

}

