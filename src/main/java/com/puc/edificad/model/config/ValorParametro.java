package com.puc.edificad.model.config;

import com.jnunes.spgcore.model.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_valor_parametro", allocationSize = 1)
public class ValorParametro extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -3502058129495047801L;

    @ManyToOne
    @JoinColumn(name = "parametro_fk")
    private Parametro parametro;

}
