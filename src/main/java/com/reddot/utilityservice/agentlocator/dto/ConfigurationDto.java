package com.reddot.utilityservice.agentlocator.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDto {
    private String key;
    private Double value;
}
