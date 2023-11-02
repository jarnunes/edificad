package com.puc.edificad.services;

import com.puc.edificad.model.Beneficiario;

import java.util.List;


public interface BeneficiarioService extends BaseService<Beneficiario> {

    List<Beneficiario> findByIdNomeCpf(Long id, String nome, String cpf);
}
