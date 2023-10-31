package com.puc.edificad.mapper;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.services.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeneficiarioMapper {

    private BeneficiarioService beneficiarioService;

    @Autowired
    public void setBeneficiarioService(BeneficiarioService serviceIn) {
        this.beneficiarioService = serviceIn;
    }

    public BeneficiarioDto toDto(Beneficiario entity) {
        BeneficiarioDto dto = new BeneficiarioDto();
        dto.copy(entity);
        return dto;
    }


}
