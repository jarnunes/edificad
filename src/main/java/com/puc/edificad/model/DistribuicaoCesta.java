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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_fk")
    private Beneficiario beneficiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntario_fk")
    private Voluntario voluntario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cesta_fk")
    private Cesta cesta;

}
