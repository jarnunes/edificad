package com.puc.edificad.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "endereco", sequenceName = "seq_endereco", allocationSize = 1)
public class Endereco extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 8102073050405161584L;
    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false, length = 100)
    private String numero;

    @Column(unique = true, length = 14)
    private String cep;

    private String bairro;

    private String cidade;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.MG;


}
