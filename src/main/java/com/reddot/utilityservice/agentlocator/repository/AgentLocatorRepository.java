package com.reddot.utilityservice.agentlocator.repository;

import com.reddot.utilityservice.agentlocator.dto.NearestAgent;
import com.reddot.utilityservice.agentlocator.entity.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AgentLocatorRepository extends JpaRepository<AgentEntity, UUID> {
    AgentEntity findByAgentWalletNo(String agentWalletNo);
    @Query(value = "SELECT * FROM (SELECT agent.agent_name as agentName, agent.agent_wallet_no as agentWalletNo, agent.latitude as latitude, agent.longitude as longitude, (6371 * acos(cos(radians_test(:latitude)) * cos(radians_test(agent.latitude)) * cos(radians_test(agent.longitude) - radians_test(:longitude)) + sin(radians_test(:latitude)) * sin(radians_test(agent.latitude)))) AS distance FROM agent_locations agent) WHERE distance <= :maxDistance ORDER BY distance ASC FETCH FIRST :listLimit ROWS ONLY", nativeQuery = true)
    List<NearestAgent> findNearestAgent(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("maxDistance") Double maxDistance, @Param("listLimit") Integer listLimit);
}
