package com.puc.edificad.services;

import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.jnunes.spgcore.service.BaseServiceImpl;
import com.puc.edificad.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VoluntarioServiceImpl extends BaseServiceImpl<Voluntario> implements VoluntarioService {

    private VoluntarioRepository repository;

    @Autowired
    public void setRepository(VoluntarioRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Override
    public List<Voluntario> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public Voluntario save(Voluntario entity) {
        ValidationUtils.validateDateTimeAfterNow(entity.getDataNascimento());
        return super.save(entity);
    }

    @Override
    public Voluntario update(Voluntario entity) {
        ValidationUtils.validateDateTimeAfterNow(entity.getDataNascimento());
        return super.update(entity);
    }

    @Override
    public Optional<Voluntario> getEntityWithSearchAttrs(String searchValue) {
        Voluntario entitySearchExample = new Voluntario();
        entitySearchExample.setNome(searchValue);
        entitySearchExample.setCpf(searchValue);
        entitySearchExample.setNumeroProjetosParticipados(null);
        return Optional.of(entitySearchExample);
    }
}
