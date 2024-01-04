package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseServiceImpl;
import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.Cesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CestaServiceImpl extends BaseServiceImpl<Cesta> implements CestaService {

    private CestaRepository repository;

    @Autowired
    public void setRepository(CestaRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Override
    public List<Cesta> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public void darBaixaDistribuicaoCesta(Long idCesta, Integer quantidade) {
        Cesta cesta = repository.findById(idCesta).orElseThrow(EntityNotFoundException::notFoundForId);
        cesta.setQuantidadeEstoque(cesta.getQuantidadeEstoque() - quantidade);
        super.update(cesta);
    }

    @Override
    public Optional<Cesta> getEntityWithSearchAttrs(String searchValue) {
        Cesta entitySearchExample = new Cesta();
        entitySearchExample.setNome(searchValue);
        return Optional.of(entitySearchExample);
    }
}
