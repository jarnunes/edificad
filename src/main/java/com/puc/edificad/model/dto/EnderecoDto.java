package com.puc.edificad.model.dto;

import com.puc.edificad.model.Endereco;
import com.puc.edificad.model.Estado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1033999706531398559L;

    private Long id;
    private String logradouro;
    private String numero;
    private String cep;
    private String bairro;
    private String cidade;
    private Estado estado;


    public void copy(Endereco entity) {
        if (entity != null) {
            setId(entity.getId());
            setLogradouro(entity.getLogradouro());
            setNumero(entity.getNumero());
            setCep(entity.getCep());
            setBairro(entity.getBairro());
            setCidade(entity.getCidade());
            setEstado(entity.getEstado());
        }
    }
}
