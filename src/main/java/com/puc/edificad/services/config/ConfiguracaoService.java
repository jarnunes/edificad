package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseService;
import com.puc.edificad.model.config.Configuracao;

import java.util.Optional;


public interface ConfiguracaoService extends BaseService<Configuracao> {

    Optional<Configuracao> findFirstConfiguracao();

    Configuracao obterConfiguracao();
}
