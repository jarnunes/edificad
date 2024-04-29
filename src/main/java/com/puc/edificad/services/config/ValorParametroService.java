package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseService;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.config.ValorParametro;
import com.puc.edificad.model.config.ValorParametroLogico;
import com.puc.edificad.model.config.ValorParametroNumerico;

public interface ValorParametroService extends BaseService<ValorParametro> {

    ValorParametroLogico obterValorParametroLogico(TipoParametroConfiguracao parametro);

    ValorParametroNumerico obterValorParametroNumerico(TipoParametroConfiguracao parametro);

}
