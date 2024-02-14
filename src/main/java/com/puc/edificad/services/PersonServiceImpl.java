package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseService;
import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.model.Pessoa;
import com.puc.edificad.services.validation.PessoaValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public abstract class PersonServiceImpl<T extends Pessoa> extends BaseServiceImpl<T> implements BaseService<T> {

    @Override
    public T save(T entity) {
        PessoaValidation validation = new PessoaValidation(entity);
        validation.validarDataNascimento();
        validation.validarEmail();
        return super.save(entity);
    }

    @Override
    public T update(T entity) {
        PessoaValidation validation = new PessoaValidation(entity);
        validation.validarDataNascimento();
        validation.validarEmail();
        return super.update(entity);
    }

}
