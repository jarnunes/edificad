package com.puc.edificad.services;

import com.puc.edificad.model.Cesta;

import java.util.List;


public interface CestaService extends BaseService<Cesta> {
    List<Cesta> findByIdNome(Long id, String nome);
}
