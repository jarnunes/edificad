package com.puc.edificad.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "dependente", sequenceName = "seq_dependente", allocationSize = 1)
public class Dependente extends Pessoa {
    @Serial
    private static final long serialVersionUID = 3822745828763545687L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiario_fk")
    private Beneficiario beneficiario;
}
