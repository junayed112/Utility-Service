package com.reddot.utilityservice.agentlocator.service;

import com.reddot.utilityservice.agentlocator.dto.AgentDto;
import com.reddot.utilityservice.agentlocator.dto.AgentLocatorResponse;

import java.util.List;

public interface AgentLocatorService {
    AgentLocatorResponse saveOrUpdateAgentLocation(AgentDto agentDto, String headerSecretKey);
    List<AgentLocatorResponse> getNearestAgent(Double latitude, Double longitude, String headerSecretKey);
    List<AgentLocatorResponse> allAgentFromDB(String headerSecretKey);
}
