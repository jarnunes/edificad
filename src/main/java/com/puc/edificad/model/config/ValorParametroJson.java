package com.puc.edificad.model.config;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ValorParametroJson extends ValorParametro {

    @Serial
    private static final long serialVersionUID = 6573085702022359465L;

    @Lob
    private String valor;
}
