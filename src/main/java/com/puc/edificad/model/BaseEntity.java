package com.puc.edificad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4918450243438648047L;

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @JsonIgnore
    @CreatedBy
    @Column(updatable = false)
    private String createUser;

    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedUser;
}
