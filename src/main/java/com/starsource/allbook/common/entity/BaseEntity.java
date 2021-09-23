package com.starsource.allbook.common.entity;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDatetime;
    @LastModifiedBy
    private String modifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;
}
