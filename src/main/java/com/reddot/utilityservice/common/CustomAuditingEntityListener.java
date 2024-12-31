package com.reddot.utilityservice.common;


import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.UUID;


@Configurable
public class CustomAuditingEntityListener {

  @PrePersist
  public void onPrePersist(BaseEntity baseEntity) {
    if (null == baseEntity.getId()) {
      setUUID(baseEntity);
    }
  }

  @PreUpdate
  public void onPreUpdate(BaseEntity baseEntity) {}

  private void setUUID(BaseEntity baseEntity) {
    baseEntity.setId(UUID.randomUUID());
  }
}
