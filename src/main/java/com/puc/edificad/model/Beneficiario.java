package com.puc.edificad.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"dependentes"})
@SequenceGenerator(name = "beneficiario", sequenceName = "seq_beneficiario", allocationSize = 1)
public class Beneficiario extends Pessoa {

    @Serial
    private static final long serialVersionUID = 3614891896109868616L;

    @OneToMany(mappedBy = "beneficiario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dependente> dependentes = new HashSet<>();

    @OneToMany(mappedBy = "beneficiario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DistribuicaoCesta> cestas = new HashSet<>();
}
