package com.reddot.utilityservice.agentlocator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentLocatorResponse {
    private String agentName;
    //private String agentWalletNo;
    private Double latitude;
    private Double longitude;
    private Double distance;
}
