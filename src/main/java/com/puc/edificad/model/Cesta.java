package com.puc.edificad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jnunes.spgcore.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_cesta", allocationSize = 1)
public class Cesta extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3705931356023443175L;

    @NotBlank(message = "cesta.nome.obrigatorio")
    @NotNull(message = "cesta.nome.obrigatorio")
    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Lob
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "cesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DistribuicaoCesta> cestas = new HashSet<>();
}
