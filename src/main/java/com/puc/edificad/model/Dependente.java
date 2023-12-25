package com.puc.edificad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = "responsavel")
//@SequenceGenerator(name = "seq_generator", sequenceName = "seq_dependente", allocationSize = 1)
public class Dependente extends Pessoa {
    @Serial
    private static final long serialVersionUID = 3822745828763545687L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_fk", nullable = false)
    private Beneficiario responsavel;
}
