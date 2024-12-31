package com.reddot.utilityservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Configuration
@EnableJpaAuditing(
    dateTimeProviderRef = "auditingDateTimeProvider")
public class JpaAuditingConfiguration {

//  @Value("${spring.application.name}")
//  private String appName;

  @Bean(name = "auditingDateTimeProvider")
  public DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(OffsetDateTime.now());
  }

}
