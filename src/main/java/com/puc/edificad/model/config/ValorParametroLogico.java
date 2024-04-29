package com.puc.edificad.model.config;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_valor_parametro_logico", allocationSize = 1)
public class ValorParametroLogico extends ValorParametro {


    @Serial
    private static final long serialVersionUID = 6573085702022359465L;

    private Boolean valor;
}
