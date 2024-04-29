package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseService;
import com.puc.edificad.model.config.Parametro;
import com.puc.edificad.model.config.TipoParametroConfiguracao;

public interface ParametroService extends BaseService<Parametro> {

    Parametro obterParametroPorNome(TipoParametroConfiguracao nome);
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

}
