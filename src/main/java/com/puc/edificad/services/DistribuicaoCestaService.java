package com.puc.edificad.services;

import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;

import java.time.LocalDate;
import java.util.List;


public interface DistribuicaoCestaService extends BaseService<DistribuicaoCesta> {

    DistribuicaoCestaDto save(DistribuicaoCestaDto dto);

    void update(DistribuicaoCestaDto dto);

    List<DistribuicaoCestaDto> findAllDto();

    List<DistribuicaoCestaDto> findBy(String cesta, String cpfBeneficiario, String cpfVoluntario, LocalDate data);
}
