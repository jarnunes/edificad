package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.services.config.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CestaServiceImpl extends BaseServiceImpl<Cesta> implements CestaService {

    private CestaRepository repository;
    private ParametroService parametroService;

    @Autowired
    public void setRepository(CestaRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Autowired
    public void setParametroService(ParametroService serviceIn){
        this.parametroService = serviceIn;
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

    @Override
    public Integer obterQuantidadeEstoque(Long idCesta) {
        return repository.findById(idCesta).map(Cesta::getQuantidadeEstoque).orElseThrow();
    }

    @Override
    public void contabilizarCestaEmEstoqueNoCancelamentoDistribuicaoCesta(Long idCesta) {
        if(parametroService.contabilizarEstoqueDepoisCancelamentoDistribuicaoCesta()){
            findById(idCesta).ifPresent(cesta -> {
                cesta.setQuantidadeEstoque(cesta.getQuantidadeEstoque() + 1);
                update(cesta);
            });
        }
    }
}
