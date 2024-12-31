package com.reddot.utilityservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureListResponse {

    private String sectionTitle;
    private Boolean is_visible;
    private List<FeatureItem> services = new ArrayList<FeatureItem>();
}
