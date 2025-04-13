package com.contact_notes_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

// This Entity will have the Audit information and which are common for more tables.
//This will avoid the code duplication and uses the code re-usability using inheritance.
@MappedSuperclass
@Getter
@Setter
public abstract class AuditEntity {
    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    protected LocalDateTime updatedAt;
    protected String createdBy;
    protected String deletedBy;

    protected String isDeleted;

    @PrePersist
    public void prePersist() {
        if (isDeleted == null) {
            isDeleted = "N";
        }
    }

}
