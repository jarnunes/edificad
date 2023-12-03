package com.puc.edificad.commons.builder;

import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.model.dto.CestaDto;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.model.dto.VoluntarioDto;

import java.time.LocalDateTime;

public class DistribuicaoCestaBuilder {

    private final DistribuicaoCestaDto distribuicaoCestaDto;


    public DistribuicaoCestaBuilder() {
        distribuicaoCestaDto = new DistribuicaoCestaDto();
    }

    public DistribuicaoCestaBuilder comVoluntario(VoluntarioDto voluntario) {
        distribuicaoCestaDto.setVoluntario(voluntario);
        return this;
    }

    public DistribuicaoCestaBuilder comBeneficiario(BeneficiarioDto beneficiario) {
        distribuicaoCestaDto.setBeneficiario(beneficiario);
        return this;
    }

    public DistribuicaoCestaBuilder comCesta(CestaDto cestaDto) {
        distribuicaoCestaDto.setCesta(cestaDto);
        return this;
    }

    public DistribuicaoCestaBuilder comDataHora(LocalDateTime dataHora) {
        distribuicaoCestaDto.setDataHora(dataHora);
        return this;
    }


    public DistribuicaoCestaDto build() {
        return distribuicaoCestaDto;
    }
}
