package com.puc.edificad.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_dist_cesta", allocationSize = 1)
public class DistribuicaoCesta extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3705931356023443175L;
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "beneficiario_fk", nullable = false)
    private Beneficiario beneficiario;

    @ManyToOne
    @JoinColumn(name = "voluntario_fk", nullable = false)
    private Voluntario voluntario;

    @ManyToOne
    @JoinColumn(name = "cesta_fk", nullable = false)
    private Cesta cesta;

}
