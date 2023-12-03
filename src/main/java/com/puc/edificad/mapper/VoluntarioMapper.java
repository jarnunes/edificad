package com.puc.edificad.mapper;

import com.puc.edificad.model.Voluntario;
import com.puc.edificad.model.dto.VoluntarioDto;
import org.springframework.stereotype.Service;

@Service
public class VoluntarioMapper {

    public VoluntarioDto toDto(Voluntario entity) {
        VoluntarioDto dto = new VoluntarioDto();
        dto.copy(entity);
        return dto;
    }

    public Voluntario toEntity(VoluntarioDto dto) {
        Voluntario entity = new Voluntario();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());
        entity.setDataNascimento(dto.getDataNascimento());
        return entity;
    }
}
