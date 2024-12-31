package com.reddot.utilityservice.agentlocator.dto;

public interface NearestAgent {
    String getAgentName();
    String getAgentWalletNo();
    Double getLatitude();
    Double getLongitude();
    Double getDistance();
}
