package com.puc.edificad.model.dto;

import com.puc.edificad.model.Cesta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CestaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4764481003897956519L;

    private Long id;

    private String nome;

    public void copy(Cesta entity) {
        setId(entity.getId());
        setNome(entity.getNome());
    }
}
