package com.puc.edificad.mapper;

import com.puc.edificad.model.Endereco;
import com.puc.edificad.model.Pessoa;
import com.puc.edificad.model.dto.EnderecoDto;
import com.puc.edificad.model.dto.PessoaDto;
import com.puc.edificad.services.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoMapper {

    private EnderecoRepository repository;

    @Autowired
    public void setRepository(EnderecoRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    public EnderecoDto toDto(Pessoa entity) {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.copy(entity.getEndereco());
        return enderecoDto;
    }

    public Endereco toEntity(PessoaDto dto) {
        EnderecoDto enderecoDto = Optional.ofNullable(dto.getEndereco()).orElse(new EnderecoDto());
        Endereco endereco = getInstanceEndereco(enderecoDto.getId());
        endereco.setLogradouro(enderecoDto.getLogradouro());
        endereco.setNumero(enderecoDto.getNumero());
        endereco.setCep(enderecoDto.getCep());
        endereco.setBairro(enderecoDto.getBairro());
        endereco.setCidade(enderecoDto.getCidade());
        endereco.setEstado(enderecoDto.getEstado());
        return endereco;
    }

    public void copy(EnderecoDto source, Endereco target){
        target.setLogradouro(source.getLogradouro());
        target.setNumero(source.getNumero());
        target.setCep(source.getCep());
        target.setBairro(source.getBairro());
        target.setCidade(source.getCidade());
        target.setEstado(source.getEstado());
    }

    private Endereco getInstanceEndereco(Long id) {
        return Optional.ofNullable(id).flatMap(repository::findById).orElse(new Endereco());
    }

}
