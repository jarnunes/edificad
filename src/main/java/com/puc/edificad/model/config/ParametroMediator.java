package com.puc.edificad.model.config;

import com.jnunes.spgcore.commons.context.StaticContextAccessor;
import com.puc.edificad.services.config.ParametroRepository;
import com.puc.edificad.services.config.ValorParametroJsonRepository;
import com.puc.edificad.services.config.ValorParametroLogicoRepository;
import com.puc.edificad.services.config.ValorParametroNumericoRepository;

public interface ParametroMediator {

    String name();

    TipoDataTypeParametro getDType();

    Parametro getInstance();

    Parametro getNewInstance();

    default <T extends ValorParametro> void save(T entity) {
        if (entity.getParametro() == null || entity.getParametro().getId() == null)
            entity.setParametro(getInstance());

        Parametro parametro = entity.getParametro();
        if (parametro.getId() == null)
            StaticContextAccessor.getBean(ParametroRepository.class).save(parametro);

        if (entity instanceof ValorParametroLogico valorParametroLogico) {
            StaticContextAccessor.getBean(ValorParametroLogicoRepository.class).save(valorParametroLogico);
        } else if (entity instanceof ValorParametroNumerico valorParametroNumerico) {
            StaticContextAccessor.getBean(ValorParametroNumericoRepository.class).save(valorParametroNumerico);
        } else if(entity instanceof ValorParametroJson valorParametroJson){
            StaticContextAccessor.getBean(ValorParametroJsonRepository.class).save(valorParametroJson);
        }
    }

}
