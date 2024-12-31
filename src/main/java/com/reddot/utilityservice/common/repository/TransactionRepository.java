package com.reddot.utilityservice.common.repository;

import com.reddot.utilityservice.common.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public  interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    Optional<TransactionEntity> findAllByTransactionId(String transactionId);

}
