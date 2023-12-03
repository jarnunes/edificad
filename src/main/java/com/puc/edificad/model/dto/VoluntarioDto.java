package com.puc.edificad.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.puc.edificad.model.Voluntario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class VoluntarioDto extends PessoaDto {

    @Serial
    private static final long serialVersionUID = 1033999706531398559L;

    @JsonProperty(required = true)
    private Integer qtdeProjetosParticipado;


    public void copy(Voluntario entity) {
        super.copy(entity);
        this.setQtdeProjetosParticipado(entity.getNumeroProjetosParticipados());
    }
}
