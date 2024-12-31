package com.reddot.utilityservice.features.desco.postpaid.repository;

import com.reddot.utilityservice.features.desco.postpaid.dto.DescoPostpaidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public  interface DescoPostpaidRepository extends JpaRepository<DescoPostpaidEntity, UUID> {
}
