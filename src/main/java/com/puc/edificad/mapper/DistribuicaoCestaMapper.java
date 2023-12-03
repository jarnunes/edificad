package com.puc.edificad.mapper;

import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.model.dto.CestaDto;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.model.dto.VoluntarioDto;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistribuicaoCestaMapper {

    private BeneficiarioService beneficiarioService;
    private VoluntarioService voluntarioService;
    private CestaService cestaService;
    private BeneficiarioMapper beneficiarioMapper;
    private CestaMapper cestaMapper;
    private VoluntarioMapper voluntarioMapper;

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.beneficiarioService = serviceIn;
    }

    @Autowired
    public void setVoluntarioService(VoluntarioService serviceIn) {
        this.voluntarioService = serviceIn;
    }

    @Autowired
    public void setCestaService(CestaService serviceIn) {
        this.cestaService = serviceIn;
    }

    @Autowired
    public void setCestaMapper(CestaMapper cestaMapperIn){
        this.cestaMapper = cestaMapperIn;
    }

    @Autowired
    public void setBeneficiarioMapper(BeneficiarioMapper beneficiarioMapperIn){
        this.beneficiarioMapper  =beneficiarioMapperIn;
    }

    @Autowired
    public void setVoluntarioMapper(VoluntarioMapper voluntarioMapperIn){
        this.voluntarioMapper = voluntarioMapperIn;
    }
    public DistribuicaoCestaDto toDto(DistribuicaoCesta entity) {
        DistribuicaoCestaDto dto = new DistribuicaoCestaDto();
        dto.setId(entity.getId());
        dto.setCesta(cestaMapper.toDto(entity.getCesta()));
        dto.setVoluntario(voluntarioMapper.toDto(entity.getVoluntario()));
        dto.setBeneficiario(beneficiarioMapper.toDto(entity.getBeneficiario()));
        dto.setDataHora(entity.getDataHora());
        return dto;
    }

    public List<DistribuicaoCestaDto> toDtoList(List<DistribuicaoCesta> entities) {
        return entities.stream().map(this::toDto).toList();
    }

    public DistribuicaoCesta toEntity(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = new DistribuicaoCesta();
        entity.setId(dto.getId());
        entity.setDataHora(dto.getDataHora());
        Optional.of(dto).map(DistribuicaoCestaDto::getCesta).map(CestaDto::getId).flatMap(cestaService::findById)
            .ifPresent(entity::setCesta);
        Optional.of(dto).map(DistribuicaoCestaDto::getVoluntario).map(VoluntarioDto::getId)
            .flatMap(voluntarioService::findById).ifPresent(entity::setVoluntario);
        Optional.of(dto).map(DistribuicaoCestaDto::getBeneficiario).map(BeneficiarioDto::getId)
            .flatMap(beneficiarioService::findById).ifPresent(entity::setBeneficiario);
        return entity;
    }
}
