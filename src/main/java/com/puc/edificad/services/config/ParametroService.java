package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseService;
import com.jnunes.spgparameter.model.Parameter;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.dto.ConfiguracaoDashboardDto;

public interface ParametroService extends BaseService<Parameter> {

    /**
     * @return boolean
     * <p>
     * Retornar valor do parametro
     * {@link com.puc.edificad.model.config.TipoParametroConfiguracao#PERMITIR_CANCELAMENTO_DISTRIBUICAO_CESTA}
     */
    boolean permitirCancelamentoDistribuicaoCesta();

    /**
     * @return boolean
     * <p>
     * Retornar valor do parametro
     * {@link com.puc.edificad.model.config.TipoParametroConfiguracao#CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA}
     */
    boolean contabilizarEstoqueDepoisCancelamentoDistribuicaoCesta();

    ConfiguracaoDashboardDto obterConfiguracaoDashboard();

}
