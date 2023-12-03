package com.puc.edificad.services;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.BeneficiarioDto;

import java.util.List;


public interface BeneficiarioService extends BaseService<Beneficiario> {

    List<Beneficiario> findByIdNomeCpf(Long id, String nome, String cpf);

    Beneficiario update(BeneficiarioDto dto);
}
