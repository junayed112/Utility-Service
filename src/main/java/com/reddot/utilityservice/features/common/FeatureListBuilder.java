package com.reddot.utilityservice.features.common;

import com.reddot.utilityservice.common.FeatureItem;
import com.reddot.utilityservice.common.FeatureListResponse;
import com.reddot.utilityservice.enums.*;
import com.reddot.utilityservice.features.wasa.dto.WasaInfoEnum;
import com.reddot.utilityservice.widgets.FieldItem;
import com.reddot.utilityservice.widgets.UtilityDetail;

import java.util.ArrayList;
import java.util.List;

public class FeatureListBuilder {

    public static FeatureListResponse getFeatureList(String localType){


       FeatureListResponse featureListResponse = new FeatureListResponse();

       ArrayList<FeatureItem> featureItems  = new ArrayList<>();
       FeatureItem featureItem1 = FeatureItem.builder()
               .feature_title(localType.equals("en")? UtilityTitle.WASA.en: UtilityTitle.WASA.bn)
               .feature_code(UtilityCode.WASA.code)
               .icon_url("http://api.sslwireless.com/uploads/utility_logo/wasa.png")
               .flow(1L)
               .is_enabled(true)
               .is_visible(true).build();

        FeatureItem featureItem2 = FeatureItem.builder()
                .feature_title(localType.equals("en")? UtilityTitle.DESCO_PREPAID.en: UtilityTitle.DESCO_PREPAID.bn)
                .feature_code(UtilityCode.DESCO_PREPAID.code)
                .icon_url("http://api.sslwireless.com/uploads/utility_logo/desco.png")
                .flow(2L)
                .is_enabled(false)
                .is_visible(true).build();

        FeatureItem featureItem3 = FeatureItem.builder()
                .feature_title(localType.equals("en")? UtilityTitle.TITAS_GAS_NON_METERED.en: UtilityTitle.TITAS_GAS_NON_METERED.bn)
                .feature_code(UtilityCode.TITAS_GAS_NON_METERED.code)

                .icon_url("http://api.sslwireless.com/uploads/utility_logo/titas.png")
                .flow(1L)
                .is_enabled(false)
                .is_visible(true)
                .build();

        featureItems.add(featureItem1);
        featureItems.add(featureItem2);
        featureItems.add(featureItem3);

        featureListResponse.setServices(featureItems);
        featureListResponse.setIs_visible(true);
        featureListResponse.setSectionTitle(localType.equals("en")? "Bill payment": "বিল পেমেন্ট");

        return featureListResponse;
    }

}
