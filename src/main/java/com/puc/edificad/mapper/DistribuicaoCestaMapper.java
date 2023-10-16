package com.puc.edificad.mapper;

import com.puc.edificad.commons.utils.EntityUtils;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.VoluntarioService;
import com.puc.edificad.services.dto.DistribuicaoCestaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistribuicaoCestaMapper {

    private BeneficiarioService beneficiarioService;

    private VoluntarioService voluntarioService;

    private CestaService cestaService;

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

    public DistribuicaoCestaDto toDto(DistribuicaoCesta entity) {
        DistribuicaoCestaDto dto = new DistribuicaoCestaDto();
        EntityUtils.setEntityId(dto::setIdVoluntario, entity::getVoluntario);
        EntityUtils.setEntityId(dto::setIdBeneficiario, entity::getBeneficiario);
        EntityUtils.setEntityId(dto::setIdCesta, entity::getCesta);
        dto.setDataHora(entity.getDataHora());
        return dto;
    }

    public DistribuicaoCesta toEntity(DistribuicaoCestaDto dto) {
        DistribuicaoCesta entity = new DistribuicaoCesta();
        entity.setDataHora(dto.getDataHora());
        EntityUtils.setEntityLoad(entity::setCesta, dto::getIdCesta, cestaService::findById);
        EntityUtils.setEntityLoad(entity::setVoluntario, dto::getIdVoluntario, voluntarioService::findById);
        EntityUtils.setEntityLoad(entity::setBeneficiario, dto::getIdBeneficiario, beneficiarioService::findById);
        return entity;
    }
}
