package com.puc.edificad.mapper;

import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.dto.CestaDto;
import com.puc.edificad.services.CestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CestaMapper {

    private CestaService service;

    @Autowired
    public void setService(CestaService serviceIn) {
        this.service = serviceIn;
    }

    public CestaDto toDto(Cesta entity) {
        CestaDto dto = new CestaDto();
        dto.copy(entity);
        return dto;
    }

    public Cesta toEntity(CestaDto dto) {
        Cesta entity = new Cesta();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        return entity;
    }
}
