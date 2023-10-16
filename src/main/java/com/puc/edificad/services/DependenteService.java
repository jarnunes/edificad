package com.puc.edificad.services;

import com.puc.edificad.model.Dependente;
import com.puc.edificad.services.dto.DependenteDto;


public interface DependenteService extends BaseService<Dependente> {

    DependenteDto save(DependenteDto dto);

    DependenteDto update(DependenteDto dto);
}
