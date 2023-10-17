package com.puc.edificad.builder;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Dependente;

public class DependenteBuilder extends PessoaBuilder<Dependente> {

    public DependenteBuilder() {
        super(new Dependente());
    }

    public DependenteBuilder comResponsavel(Beneficiario beneficiario) {
        pessoa.setResponsavel(beneficiario);
        return this;
    }

}
