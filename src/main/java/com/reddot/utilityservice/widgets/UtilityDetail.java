package com.reddot.utilityservice.widgets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilityDetail {
    String utilityCode;
    String utilityTitle;
    String utilityImage;
    String flow;
    List<FieldItem> fieldItemList;
}
