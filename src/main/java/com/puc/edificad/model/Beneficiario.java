package com.puc.edificad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode(callSuper = true, exclude = {"dependentes", "cestas"})
//@SequenceGenerator(name = "seq_generator", sequenceName = "seq_beneficiario", allocationSize = 1)
public class Beneficiario extends Pessoa {

    @Serial
    private static final long serialVersionUID = 3614891896109868616L;

    @OneToMany(mappedBy = "responsavel", fetch = FetchType.LAZY)
    private Set<Dependente> dependentes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "beneficiario", fetch = FetchType.LAZY)
    private Set<DistribuicaoCesta> cestas = new HashSet<>();
}
