package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseService;
import com.puc.edificad.model.Cesta;

import java.util.List;


public interface CestaService extends BaseService<Cesta> {
    List<Cesta> findByNome(String nome);

    void darBaixaDistribuicaoCesta(Long idCesta, Integer quantidade);
}
