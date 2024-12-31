package com.reddot.utilityservice.agentlocator.entity;

import com.reddot.utilityservice.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "agent_locations")
public class AgentEntity extends BaseEntity {

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "agent_wallet_no")
    private String agentWalletNo;
    private Double latitude;
    private Double longitude;
}
