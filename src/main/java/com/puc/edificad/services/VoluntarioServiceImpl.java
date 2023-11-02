package com.puc.edificad.services;

import com.puc.edificad.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VoluntarioServiceImpl extends BaseServiceImpl<Voluntario> implements VoluntarioService {

    private VoluntarioRepository repository;

    @Autowired
    public void setRepository(VoluntarioRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Override
    public List<Voluntario> findByIdNomeCpf(Long id, String cpf, String nome) {
        return repository.findByIdNomeCpf(id, nome, cpf);
    }
}
