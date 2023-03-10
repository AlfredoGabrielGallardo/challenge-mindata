package com.mindata.challenge.model.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class HeroAuditory {

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @PrePersist
    void updateCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    @PreUpdate
    void updateModificationDate() {
        this.modificationDate = LocalDateTime.now();
    }
}
