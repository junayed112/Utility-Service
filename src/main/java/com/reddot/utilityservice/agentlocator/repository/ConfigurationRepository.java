package com.reddot.utilityservice.agentlocator.repository;

import com.reddot.utilityservice.agentlocator.entity.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, UUID> {
    ConfigurationEntity findByConfigurationKey(String key);
}
