package com.reddot.utilityservice.requestmoney.repository;

import com.reddot.utilityservice.requestmoney.entity.RequestMoneyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestMoneyRepository extends JpaRepository<RequestMoneyEntity, UUID> {
    List<RequestMoneyEntity> findAllByRequestFrom(String requestFrom);
    List<RequestMoneyEntity> findAllByRequestTo(String requestTo);
    Page<RequestMoneyEntity> findAllByRequestFrom(String requestFrom, Pageable pageable);
    Page<RequestMoneyEntity> findAllByRequestTo(String requestTo, Pageable pageable);
    Optional<RequestMoneyEntity> findByRequestId(String requestId);
}
