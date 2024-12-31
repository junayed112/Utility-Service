package com.reddot.utilityservice.features.desco.prepaid.dto;

import com.reddot.utilityservice.enums.*;
import com.reddot.utilityservice.widgets.FieldItem;
import com.reddot.utilityservice.widgets.UtilityDetail;

import java.util.ArrayList;

public class DescoPrepaidDetailBuilder {

    public UtilityDetail getDescoPrepaidDetail(String localType){

        UtilityDetail utilityDetail = new UtilityDetail();
        utilityDetail.setUtilityCode(UtilityCode.DESCO_PREPAID.code);
        utilityDetail.setUtilityTitle(localType.equals("en")? UtilityTitle.DESCO_PREPAID.en: UtilityTitle.DESCO_PREPAID.bn);
        utilityDetail.setUtilityImage("http://api.sslwireless.com/uploads/utility_logo/desco.png");
        utilityDetail.setFlow("2");
        ArrayList<FieldItem> fieldItems  = new ArrayList<>();

        FieldItem item1 = FieldItem.builder()
                .key(Keys.ACCOUNT_NO.key)
                .label(localType.equals("en")?Labels.ACCOUNT_NO.en:Labels.ACCOUNT_NO.bn)
                .fieldType(FieldType.TEXT_FIELD.type)
                .isRequired(true)
                .dataType(DataType.TEXT.type)
                .regex(Regex.DESCO_ACCOUNT_NUMBER.value)
                .hint(Hints.ACCOUNT_NO.en)
                .build();

        FieldItem item2 = FieldItem.builder()
                .key(Keys.MOBILE_NO.key)
                .label(localType.equals("en")?Labels.MOBILE_NO.en : Labels.MOBILE_NO.bn)
                .fieldType(FieldType.TEXT_FIELD.type)
                .isRequired(true)
                .dataType(DataType.NUMERIC.type)
                .regex(Regex.MOBILE_NUMBER.value)
                .hint(Hints.ACCOUNT_NO.en)
                .build();

        FieldItem item3 = FieldItem.builder()
                .key(Keys.AMOUNT.key)
                .label(localType.equals("en")?Labels.AMOUNT.en : Labels.AMOUNT.bn)
                .fieldType(FieldType.TEXT_FIELD.type)
                .isRequired(true)
                .dataType(DataType.NUMERIC.type)
                .regex(Regex.AMOUNT.value)
                .hint(localType.equals("en")?Hints.AMOUNT.en : Hints.AMOUNT.bn)
                .build();

        fieldItems.add(item1);
        fieldItems.add(item2);
        fieldItems.add(item3);
        utilityDetail.setFieldItemList(fieldItems);

        return utilityDetail;
    }

}
