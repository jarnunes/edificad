package com.puc.edificad.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_pessoa", allocationSize = 1)
public abstract class Pessoa extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4506988147216672139L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column(unique = true, length = 16)
    private String telefone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_fk")
    private Endereco endereco;


}
