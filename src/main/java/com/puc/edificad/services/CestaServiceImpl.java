package com.puc.edificad.services;

import com.puc.edificad.model.Cesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CestaServiceImpl extends BaseServiceImpl<Cesta> implements CestaService {

    private CestaRepository repository;

    @Autowired
    public void setRepository(CestaRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Override
    public List<Cesta> findByIdNome(Long id, String nome) {
        return repository.findByIdNome(id, nome);
    }
}
