package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseService;
import com.puc.edificad.model.Voluntario;

import java.util.List;


public interface VoluntarioService extends BaseService<Voluntario> {

    List<Voluntario> findByIdNomeCpf(Long id, String cpf, String nome);
}
