package com.reddot.utilityservice.features.titas.dto;

import com.reddot.utilityservice.enums.*;
import com.reddot.utilityservice.widgets.FieldItem;
import com.reddot.utilityservice.widgets.UtilityDetail;

import java.util.ArrayList;

public class TitasDetailBuilder {

    public UtilityDetail getTitasDetail(String localType, String  titasBillType){
        UtilityDetail utilityDetail = new UtilityDetail();
        utilityDetail.setUtilityCode(titasBillType);
        utilityDetail.setUtilityImage("http://api.sslwireless.com/uploads/utility_logo/titas.png");
        utilityDetail.setFlow("1");
        ArrayList<FieldItem> fieldItems = new ArrayList<>();

        //set field item to list based on utility code
        if(titasBillType.equals(UtilityCode.TITAS_METERED.code)){
            utilityDetail.setUtilityTitle(localType.equals("en")? UtilityTitle.TITAS_METER.en : UtilityTitle.TITAS_METER.bn);

            //Field Invoice:
            FieldItem fieldItem1 = FieldItem.builder()
                    .key(Keys.INVOICE_NO.key)
                    .label(localType.equals("en")? Labels.INVOICE_NUMBER.en : Labels.INVOICE_NUMBER.bn)
                    .fieldType(FieldType.TEXT_FIELD.type)
                    .isRequired(true)
                    .dataType(DataType.NUMERIC.type)
                    .hint(localType.equals("en")? Hints.TITAS_INVOICE.en : Hints.TITAS_INVOICE.bn)
                    .build();
            fieldItems.add(fieldItem1);
        }
        else if(titasBillType.equals(UtilityCode.TITAS_NON_METERED.code)){
            utilityDetail.setUtilityTitle(localType.equals("en")? UtilityTitle.TITAS_NON_METER.en : UtilityTitle.TITAS_NON_METER.bn);
        }

        //Field Customer(Common for Meter & Non Meter):
        FieldItem fieldCommon = FieldItem.builder()
                .key(Keys.CUSTOMER_CODE.key)
                .label(localType.equals("en")? Labels.CUSTOMER_CODE.en : Labels.CUSTOMER_CODE.bn)
                .fieldType(FieldType.TEXT_FIELD.type)
                .isRequired(true)
                .dataType(DataType.NUMERIC.type)
                .hint(localType.equals("en")? Hints.TITAS_CUSTOMER_CODE.en : Hints.TITAS_CUSTOMER_CODE.bn)
                .build();

        fieldItems.add(fieldCommon);
        utilityDetail.setFieldItemList(fieldItems);

        return utilityDetail;
    }
}
