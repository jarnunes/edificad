package com.puc.edificad.services;

import com.puc.edificad.model.Beneficiario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BeneficiarioServiceImpl extends BaseServiceImpl<Beneficiario> implements BeneficiarioService {

    private BeneficiarioRepository repository;

    @Autowired
    public void setRepository(BeneficiarioRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Override
    public List<Beneficiario> findByIdNomeCpf(Long id, String nome, String cpf) {
        return repository.findByIdNomeCpf(id, nome, cpf);
    }
}
