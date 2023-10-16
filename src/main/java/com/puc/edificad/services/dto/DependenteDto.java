package com.puc.edificad.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.puc.edificad.model.Pessoa;
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

    public void copy(Pessoa entity){
        this.setId(entity.getId());
        this.setNome(entity.getNome());
        this.setEmail(entity.getEmail());
        this.setCpf(entity.getCpf());
        this.setTelefone(entity.getTelefone());
        this.setDataNascimento(entity.getDataNascimento());
    }
}
