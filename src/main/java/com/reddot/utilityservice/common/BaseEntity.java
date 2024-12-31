package com.reddot.utilityservice.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
@Accessors(chain = true)
@EntityListeners({AuditingEntityListener.class, CustomAuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {

  @Id
  @Type(type = "uuid-char")
  private UUID id;

  @CreatedDate
  @Column(name = "CREATE_DATE_TIME",updatable = false)
  private OffsetDateTime createDateTime;

  @LastModifiedDate
  @Column(name = "UPDATE_DATE_TIME")
  private OffsetDateTime updateDateTime;

  @Setter
  @Column(name = "IS_DELETED")
  private boolean deleted;

  public BaseEntity setId(String id) {
    this.id = UUID.fromString(id);
    return this;
  }

  public BaseEntity setId(UUID id) {
    this.id = id;
    return this;
  }

}
