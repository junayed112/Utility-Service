package com.reddot.utilityservice.agentlocator.service.impl;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.agentlocator.dto.ConfigurationDto;
import com.reddot.utilityservice.agentlocator.entity.ConfigurationEntity;
import com.reddot.utilityservice.agentlocator.repository.ConfigurationRepository;
import com.reddot.utilityservice.agentlocator.service.ConfigurationService;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.utils.Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final ApplicationPrperties applicationPrperties;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository, ApplicationPrperties applicationPrperties) {
        this.configurationRepository = configurationRepository;
        this.applicationPrperties = applicationPrperties;
    }

    @Override
    public ConfigurationEntity save(ConfigurationDto configurationDto, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                ConfigurationEntity configuration = configurationRepository.findByConfigurationKey(configurationDto.getKey());
                if(configuration == null){
                    configuration = new ConfigurationEntity();
                    configuration.setConfigurationKey(configurationDto.getKey());
                }
                configuration.setConfigurationValue(configurationDto.getValue());
                return configurationRepository.save(configuration);
            }catch(Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException exception){
            throw exception;
        }
    }

    @Override
    public List<ConfigurationEntity> getConfigurations(String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                return configurationRepository.findAll();
            }catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException exception){
            throw exception;
        }
    }

    @Override
    public ConfigurationEntity getConfigurationByKey(String key) {
        try{
            if(key != null && !key.isEmpty()){
                try{
                    return configurationRepository.findByConfigurationKey(key);
                }catch (Exception exception){
                    throw new ApplicationException(new CustomError("400", exception.getMessage()));
                }
            }else {
                throw new ApplicationException(new CustomError("400", "Key is required"));
            }
        }catch(ApplicationException exception){
            throw exception;
        }
    }
}
