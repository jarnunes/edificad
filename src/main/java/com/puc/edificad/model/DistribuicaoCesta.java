package com.puc.edificad.model;

import com.jnunes.spgcore.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_dist_cesta", allocationSize = 1)
public class DistribuicaoCesta extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 8419766519765604324L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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
