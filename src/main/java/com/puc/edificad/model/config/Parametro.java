package com.puc.edificad.model.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jnunes.spgcore.model.BaseEntity;
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
@EqualsAndHashCode(callSuper = true, exclude = {"valoresParametro"})
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_parametro", allocationSize = 1)
public class Parametro extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1726190866368586092L;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private TipoParametroConfiguracao nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDominioParametro dominio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDataTypeParametro dType;

    @JsonIgnore
    @OneToMany(mappedBy = "parametro", fetch = FetchType.EAGER)
    private Set<ValorParametro> valoresParametro = new HashSet<>();
}
