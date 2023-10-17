package com.puc.edificad.services;

import com.puc.edificad.model.Dependente;
import com.puc.edificad.services.dto.DependenteDto;

import java.util.List;


public interface DependenteService extends BaseService<Dependente> {

    List<DependenteDto> findAllDto();
    DependenteDto save(DependenteDto dto);

    DependenteDto update(DependenteDto dto);
}
