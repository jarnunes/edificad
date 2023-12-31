package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseService;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.dto.DependenteDto;

import java.util.List;


public interface DependenteService extends BaseService<Dependente> {

    List<DependenteDto> findAllDto();
    DependenteDto save(DependenteDto dto);

    DependenteDto update(DependenteDto dto);
}
