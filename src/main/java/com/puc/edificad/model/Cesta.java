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
@SequenceGenerator(name = "cesta", sequenceName = "seq_cesta", allocationSize = 1)
public class Cesta extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3705931356023443175L;
    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Lob
    private String descricao;

    @OneToMany(mappedBy = "cesta", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DistribuicaoCesta> cestas = new HashSet<>();
}
