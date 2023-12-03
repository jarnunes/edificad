package com.puc.edificad.model.dto;

import com.puc.edificad.model.Pessoa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PessoaDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 9135775622911235075L;
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;

    private EnderecoDto endereco;

    public void copy(Pessoa entity) {
        if (entity != null) {
            this.setId(entity.getId());
            this.setNome(entity.getNome());
            this.setEmail(entity.getEmail());
            this.setCpf(entity.getCpf());
            this.setTelefone(entity.getTelefone());
            this.setDataNascimento(entity.getDataNascimento());

            setEndereco(new EnderecoDto());
            endereco.copy(entity.getEndereco());
        }
    }
}
