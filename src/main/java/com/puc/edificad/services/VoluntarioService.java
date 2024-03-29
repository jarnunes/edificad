package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseService;
import com.puc.edificad.model.Voluntario;

import java.util.List;


public interface VoluntarioService extends BaseService<Voluntario> {

    List<Voluntario> findByNome(String nome);

}
