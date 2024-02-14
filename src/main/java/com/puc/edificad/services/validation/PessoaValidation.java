package com.puc.edificad.services.validation;

import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.puc.edificad.model.Pessoa;

public class PessoaValidation {

    protected final Pessoa entity;

    public PessoaValidation(Pessoa entityIn) {
        this.entity = entityIn;
    }

    public void validarDataNascimento() {
        ValidationUtils.validateIsAfterNow(entity.getDataNascimento(), "pessoa.data.nascimento.invalida",
            DateTimeUtils.formatter(entity.getDataNascimento()));
    }

    public void validarEmail(){
        ValidationUtils.validateEmail(entity.getEmail());
    }
}
