package com.puc.edificad.model.evento;

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
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_evento", allocationSize = 1)
public class Evento extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -2494059619925964456L;

    private String identificador;

    @Enumerated(EnumType.STRING)
    private TipoSituacaoEvento situacaoEvento = TipoSituacaoEvento.NOVO;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    private int tentativasExecucao;

    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
    private Set<ExecucaoEvento> execucoesEvento = new HashSet<>();
}
