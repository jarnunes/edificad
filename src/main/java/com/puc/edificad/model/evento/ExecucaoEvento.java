package com.puc.edificad.model.evento;

import com.jnunes.spgcore.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_execucao_evento", allocationSize = 1)
public class ExecucaoEvento extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 775648827107091601L;

    @ManyToOne
    private Evento evento;

    private byte[] conteudo;
}
