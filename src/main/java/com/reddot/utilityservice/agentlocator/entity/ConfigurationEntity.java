package com.reddot.utilityservice.agentlocator.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "configuration_list")
public class ConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String configurationKey;
    private Double configurationValue;
}
