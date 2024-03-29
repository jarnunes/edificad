package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseService;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.BeneficiarioDto;

import java.util.List;


public interface BeneficiarioService extends BaseService<Beneficiario> {

    List<Beneficiario> findByNomeCpf(String nome, String cpf);

    Beneficiario update(BeneficiarioDto dto);
}
