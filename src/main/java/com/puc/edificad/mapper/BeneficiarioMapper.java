package com.puc.edificad.mapper;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Endereco;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.model.dto.EnderecoDto;
import com.puc.edificad.services.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeneficiarioMapper {

    private BeneficiarioRepository repository;

    private EnderecoMapper enderecoMapper;

    @Autowired
    public void setRepository(BeneficiarioRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Autowired
    public void setEnderecoMapper(EnderecoMapper mapperIn) {
        this.enderecoMapper = mapperIn;
    }

    public BeneficiarioDto toDto(Beneficiario entity) {
        BeneficiarioDto dto = new BeneficiarioDto();
        dto.copy(entity);
        return dto;
    }

    public Beneficiario toEntity(BeneficiarioDto dto) {
        Beneficiario entity = getInstance(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());
        entity.setDataNascimento(dto.getDataNascimento());

        EnderecoDto enderecoDto = Optional.ofNullable(dto.getEndereco()).orElse(new EnderecoDto());
        Endereco endereco = getInstanceEndereco(entity);
        enderecoMapper.copy(enderecoDto, endereco);
        entity.setEndereco(endereco);
        return entity;
    }

    private Beneficiario getInstance(Long id) {
        return Optional.ofNullable(id).flatMap(repository::findById).orElse(new Beneficiario());
    }

    private Endereco getInstanceEndereco(Beneficiario entity) {
        return Optional.ofNullable(entity).map(Beneficiario::getEndereco).orElse(new Endereco());
    }

}
