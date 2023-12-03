package com.puc.edificad.mapper;

import com.puc.edificad.commons.utils.EntityUtils;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.dto.DependenteDto;
import com.puc.edificad.services.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DependenteMapper {

    private BeneficiarioService beneficiarioService;

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.beneficiarioService = serviceIn;
    }

    public DependenteDto toDto(Dependente entity) {
        DependenteDto dto = new DependenteDto();
        dto.copy(entity);
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setCpf(entity.getCpf());
        dto.setTelefone(entity.getTelefone());
        dto.setDataNascimento(entity.getDataNascimento());
        EntityUtils.setEntityId(dto::setIdResponsavel, entity::getResponsavel);
        return dto;
    }

    public Dependente toEntity(DependenteDto dto) {
        Dependente dependente = new Dependente();
        dependente.setId(dto.getId());
        dependente.setNome(dto.getNome());
        dependente.setEmail(dto.getEmail());
        dependente.setCpf(dto.getCpf());
        dependente.setTelefone(dto.getTelefone());
        dependente.setDataNascimento(dto.getDataNascimento());
        EntityUtils.setEntityLoad(dependente::setResponsavel, dto::getIdResponsavel, beneficiarioService::findById);
        return dependente;
    }
}
