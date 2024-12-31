package com.reddot.utilityservice.agentlocator.service;

import com.reddot.utilityservice.agentlocator.dto.ConfigurationDto;
import com.reddot.utilityservice.agentlocator.entity.ConfigurationEntity;

import java.util.List;

public interface ConfigurationService {
    ConfigurationEntity save(ConfigurationDto configuration, String headerSecretKey);
    List<ConfigurationEntity> getConfigurations(String headerSecretKey);
    ConfigurationEntity getConfigurationByKey(String key);
}
