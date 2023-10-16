package com.puc.edificad.services;

import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.services.dto.DistribuicaoCestaDto;


public interface DistribuicaoCestaService extends BaseService<DistribuicaoCesta> {

    DistribuicaoCestaDto save(DistribuicaoCestaDto dto);

    DistribuicaoCestaDto update(DistribuicaoCestaDto dto);
}
