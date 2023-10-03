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
public class Dependente extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 3822745828763545687L;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, length = 14)
    private String cpf;

    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Beneficiario beneficiario;
}
