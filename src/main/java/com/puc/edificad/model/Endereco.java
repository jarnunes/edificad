package com.puc.edificad.model;

import com.jnunes.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_endereco", allocationSize = 1)
public class Endereco extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 8102073050405161584L;
    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false, length = 100)
    private String numero;

    @Column(length = 14)
    private String cep;

    @Column(length = 100)
    private String bairro;

    @Column(length = 50)
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    private Estado estado = Estado.MG;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(logradouro).append(", ");
        sb.append("nro ").append(numero).append(" - ");
        sb.append(bairro).append(" ");
        sb.append(cep).append(", ");
        sb.append(cidade).append("/");
        sb.append(estado);
        return sb.toString();
    }
}
