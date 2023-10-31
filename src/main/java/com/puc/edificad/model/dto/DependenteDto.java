package com.puc.edificad.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.puc.edificad.model.Dependente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class DependenteDto extends PessoaDto {

    @Serial
    private static final long serialVersionUID = 1033999706531398559L;

    @JsonProperty(required = true)
    private Long idResponsavel;

    public void copy(Dependente entity) {
        super.copy(entity);
        this.setIdResponsavel(entity.getResponsavel().getId());
    }
}
