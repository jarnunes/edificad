package com.puc.edificad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "person", sequenceName = "seq_person", allocationSize = 1)
public class Person extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 4864775366433812063L;
    private String nome;
    private String email;
}
