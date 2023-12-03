package com.puc.edificad.commons.builder;

import com.puc.edificad.model.Configuracao;

public class ConfiguracaoBuilder {

    private final Configuracao configuracao;


    public ConfiguracaoBuilder() {
        configuracao = new Configuracao();
    }

    public ConfiguracaoBuilder comToken(String token) {
        configuracao.setTokenSecretKey(token);
        return this;
    }

    public ConfiguracaoBuilder comDuracaoValidadeToken(Integer duracaoEmMinutos) {
        configuracao.setTokenExpiresAt(duracaoEmMinutos);
        return this;
    }

    public Configuracao build() {
        return configuracao;
    }
}
