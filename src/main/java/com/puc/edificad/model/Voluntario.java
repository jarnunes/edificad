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
@EqualsAndHashCode(callSuper = true, exclude = {"cestas"})
public class Voluntario extends Pessoa {
    @Serial
    private static final long serialVersionUID = 6305563136938823147L;

    private Integer numeroProjetosParticipados = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "voluntario", fetch = FetchType.LAZY)
    private Set<DistribuicaoCesta> cestas = new HashSet<>();
}
