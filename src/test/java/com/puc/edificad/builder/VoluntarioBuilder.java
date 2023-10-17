package com.puc.edificad.builder;

import com.puc.edificad.model.Voluntario;

public class VoluntarioBuilder extends PessoaBuilder<Voluntario> {

    public VoluntarioBuilder() {
        super(new Voluntario());
    }

    public VoluntarioBuilder comParticipacaoEmProjetos(Integer numeroDeProjetosParticipantes) {
        pessoa.setNumeroProjetosParticipados(numeroDeProjetosParticipantes);
        return this;
    }


}
