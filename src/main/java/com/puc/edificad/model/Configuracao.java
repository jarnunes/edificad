package com.puc.edificad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_configuracao", allocationSize = 1)
public class Configuracao extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 7258385743541484317L;
    private String tokenSecretKey;

    private Integer tokenExpiresAt;
}
