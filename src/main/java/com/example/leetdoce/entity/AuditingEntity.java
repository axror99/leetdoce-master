package com.example.leetdoce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity {

    @JsonIgnore
    @CreatedBy
    protected String createdBy;

    @JsonIgnore
    @CreationTimestamp
    protected Timestamp createdDate;

    @JsonIgnore
    @UpdateTimestamp
    protected Timestamp updatedDAte;
}
