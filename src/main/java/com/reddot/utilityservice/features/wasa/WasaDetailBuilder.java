package com.reddot.utilityservice.features.wasa;

import com.reddot.utilityservice.enums.*;
import com.reddot.utilityservice.features.wasa.dto.WasaInfoEnum;
import com.reddot.utilityservice.widgets.FieldItem;
import com.reddot.utilityservice.widgets.UtilityDetail;

import java.util.ArrayList;

public class WasaDetailBuilder {


    public UtilityDetail getWasaDetail(String localType){

        UtilityDetail utilityDetail = new UtilityDetail();
        utilityDetail.setUtilityCode(UtilityCode.WASA.code);
        utilityDetail.setUtilityTitle(localType.equals("en")? UtilityTitle.WASA.en: UtilityTitle.WASA.bn);
        utilityDetail.setUtilityImage("http://api.sslwireless.com/uploads/utility_logo/wasa.png");
        utilityDetail.setFlow("1");
        ArrayList<FieldItem> fieldItems  = new ArrayList<>();

        FieldItem item1 = FieldItem.builder()
                .key(Keys.ACCOUNT_NO.key)
                .label(localType.equals("en")?Labels.ACCOUNT_NO.en:Labels.ACCOUNT_NO.bn)
                .fieldType(FieldType.TEXT_FIELD.type)
                .isRequired(true)
                .dataType(DataType.TEXT.type)
                .regex(WasaInfoEnum.REGEX_ACCOUNT_NUMBER.value)
                .hint(Hints.WASA_ACCOUNT_NO.en)
                .build();

        FieldItem item2 = FieldItem.builder()
                .key(Keys.BILL_NO.key)
                .label(localType.equals("en")?Labels.BILL_NO.en : Labels.BILL_NO.bn)
                .fieldType(FieldType.TEXT_FIELD.type)
                .isRequired(true)
                .dataType(DataType.NUMERIC.type)
                .regex(WasaInfoEnum.REGEX_BILL_NUMBER.value)
                .hint(Hints.ACCOUNT_NO.en)
                .build();

        FieldItem item3 = FieldItem.builder()
                .key(Keys.NOTIFICATION_NUMBER.key)
                .label(localType.equals("en")?Labels.NOTIFICATION_NUMBER.en : Labels.NOTIFICATION_NUMBER.bn)
                .fieldType(FieldType.NOTIFICATION_WIDGET.type)
                .isRequired(false)
                .dataType(DataType.NUMERIC.type)
                .regex(Regex.MOBILE_NUMBER.value)
                .hint(Hints.PHONE_NUMBER.en)
                .build();

        fieldItems.add(item1);
        fieldItems.add(item2);
        fieldItems.add(item3);
        utilityDetail.setFieldItemList(fieldItems);
        return utilityDetail;
    }
}
