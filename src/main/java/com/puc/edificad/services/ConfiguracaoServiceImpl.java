package com.puc.edificad.services;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.Configuracao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ConfiguracaoServiceImpl extends BaseServiceImpl<Configuracao> implements ConfiguracaoService {

    private ConfiguracaoRepository repository;

    @Autowired
    public void setRepository(ConfiguracaoRepository repositoryIn){
        this.repository = repositoryIn;
    }

    @Override
    public Configuracao save(Configuracao entity){
        return super.save(copyToStoredEntityOrToNewInstance(entity));
    }

    @Override
    public Configuracao update(Configuracao entity){
        return super.update(copyToStoredEntityOrToNewInstance(entity));
    }

    private Configuracao copyToStoredEntityOrToNewInstance(Configuracao entity){
        Configuracao storedEntity = repository.findFirstConfiguracao().orElse(new Configuracao());
        storedEntity.setTokenExpiresAt(entity.getTokenExpiresAt());
        storedEntity.setTokenSecretKey(entity.getTokenSecretKey());
        return storedEntity;
    }

    @Override
    public Optional<Configuracao> findFirstConfiguracao() {
        return repository.findFirstConfiguracao();
    }

    @Override
    public Configuracao obterConfiguracao() {
        return findFirstConfiguracao().orElseThrow(EntityNotFoundException::notFound);
    }
}
