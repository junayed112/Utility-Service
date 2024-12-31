package com.reddot.utilityservice.widgets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldItem {
    String key;
    String label;
    String fieldType;
    boolean isRequired;
    String dataType;
    String regex;
    String hint;
    List<String> options;
}
