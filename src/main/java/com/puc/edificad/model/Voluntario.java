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
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "voluntario", sequenceName = "seq_voluntario", allocationSize = 1)
public class Voluntario extends Pessoa {
    @Serial
    private static final long serialVersionUID = 6305563136938823147L;

    private Integer numeroProjetosParticipados;

    @OneToMany(mappedBy = "voluntario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistribuicaoCesta> cestas = new HashSet<>();
}
