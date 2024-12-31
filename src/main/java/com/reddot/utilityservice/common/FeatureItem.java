
package com.reddot.utilityservice.common;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeatureItem {

    private String feature_code;
    private String feature_title;
    private Long flow;
    private String icon_url;
    private Boolean is_enabled;
    private Boolean is_visible;

}
