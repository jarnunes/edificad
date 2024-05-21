package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseService;
import com.puc.edificad.model.config.*;

public interface ValorParametroService extends BaseService<ValorParametro> {


    ValorParametroLogico obterValorParametroLogico(TipoParametroConfiguracao parametro);

    ValorParametroNumerico obterValorParametroNumerico(TipoParametroConfiguracao parametro);

    ValorParametroJson obterValorParametroJson(TipoParametroConfiguracao parametro);

    <T extends ValorParametro> void saveByType(T entity);

}
