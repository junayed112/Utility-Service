package com.reddot.utilityservice.agentlocator.service.impl;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.agentlocator.dto.AgentDto;
import com.reddot.utilityservice.agentlocator.dto.AgentLocatorResponse;
import com.reddot.utilityservice.agentlocator.entity.AgentEntity;
import com.reddot.utilityservice.agentlocator.repository.AgentLocatorRepository;
import com.reddot.utilityservice.agentlocator.service.AgentLocatorService;
import com.reddot.utilityservice.agentlocator.service.ConfigurationService;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.utils.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentLocatorServiceImpl implements AgentLocatorService {

    private final AgentLocatorRepository agentLocatorRepository;
    private final ApplicationPrperties applicationPrperties;
    private final ConfigurationService configurationService;

    public static final String DISTANCE_KEY = "NEAREST_AGENT_WITH_IN";
    public static final String SHOWING_AGENT_KEY = "SHOWING_AGENT_AT_MOST";

    public AgentLocatorServiceImpl(AgentLocatorRepository agentLocatorRepository, ApplicationPrperties applicationPrperties, ConfigurationService configurationService) {
        this.agentLocatorRepository = agentLocatorRepository;
        this.applicationPrperties = applicationPrperties;
        this.configurationService = configurationService;
    }

    @Override
    public AgentLocatorResponse saveOrUpdateAgentLocation(AgentDto agentDto, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                AgentEntity agent = agentLocatorRepository.findByAgentWalletNo(agentDto.getAgentWalletNo());
                if(agent == null){
                    agent = new AgentEntity();
                    agent.setAgentName(agentDto.getAgentName());
                    agent.setAgentWalletNo(agentDto.getAgentWalletNo());
                }

                agent.setLatitude(agentDto.getLatitude());
                agent.setLongitude(agentDto.getLongitude());

                AgentLocatorResponse agentLocatorResponse = new AgentLocatorResponse();
                BeanUtils.copyProperties(agentLocatorRepository.save(agent), agentLocatorResponse);

                return agentLocatorResponse;
            }catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException exception){
            throw exception;
        }
    }

    @Override
    public List<AgentLocatorResponse> getNearestAgent(Double latitude, Double longitude, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            if(latitude == null){
                throw new ApplicationException(new CustomError("400", "Latitude is required"));
            }
            if(longitude == null){
                throw new ApplicationException(new CustomError("400", "Longitude is required"));
            }
            try{
                return agentLocatorRepository.findNearestAgent(latitude, longitude, configurationService.getConfigurationByKey(DISTANCE_KEY).getConfigurationValue(), configurationService.getConfigurationByKey(SHOWING_AGENT_KEY).getConfigurationValue().intValue())
                        .stream().map(nearestAgent -> new AgentLocatorResponse(
                               nearestAgent.getAgentName(),
//                               nearestAgent.getAgentWalletNo(),
                               nearestAgent.getLatitude(),
                               nearestAgent.getLongitude(),
                               nearestAgent.getDistance()))
                        .collect(Collectors.toList());
            }catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException applicationException){
            throw applicationException;
        }
    }


    @Override
    public List<AgentLocatorResponse> allAgentFromDB(String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                return agentLocatorRepository.findAll()
                        .stream().map(nearestAgent -> new AgentLocatorResponse(
                                nearestAgent.getAgentName(),
                                nearestAgent.getLatitude(),
                                nearestAgent.getLongitude(), 0.0))
                        .collect(Collectors.toList());
            }
            catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException applicationException){
            throw applicationException;
        }


    }
}
