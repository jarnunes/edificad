package com.puc.edificad.model;

import jakarta.persistence.Column;
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
@SequenceGenerator(name = "voluntario", sequenceName = "seq_voluntario", allocationSize = 1)
public class Voluntario extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 6305563136938823147L;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, length = 14)
    private String cpf;

    private String telefone;
}
