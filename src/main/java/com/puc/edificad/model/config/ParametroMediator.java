package com.puc.edificad.model.config;

import com.jnunes.spgcore.commons.context.StaticContextAccessor;
import com.puc.edificad.services.config.ValorParametroService;

public interface ParametroMediator {

    String name();

    TipoDataTypeParametro getDType();

    Parametro getInstance();

    Parametro getNewInstance();

    default <T extends ValorParametro> void save(T entity) {
        if (entity.getParametro() == null || entity.getParametro().getId() == null)
            entity.setParametro(getInstance());

        StaticContextAccessor.getBean(ValorParametroService.class).saveByType(entity);
    }

}
