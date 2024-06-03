package com.puc.edificad.model.config;

import com.jnunes.spgcore.commons.LabelMessage;
import com.jnunes.spgparameter.ParameterMediator;
import com.jnunes.spgparameter.model.DataType;
import com.puc.edificad.model.dto.ConfiguracaoDashboardDto;
import lombok.Getter;

import java.util.Map;

import static com.puc.edificad.model.config.TipoDominioParametro.*;

@Getter
public enum TipoParametroConfiguracao implements ParameterMediator, LabelMessage {

    PERMITIR_CANCELAMENTO_DISTRIBUICAO_CESTA(DISTRIBUICAO_CESTA, DataType.BOOLEAN, false,
            "Indica se pode ser cancelada distribuição de cestas"),

    CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA(CESTA, DataType.BOOLEAN, true,
            "Indica se a quantidade de cestas em estoque será incrementada após cancelar distribuição"),

    CONFIGURACAO_DASHBOARD(DASHBOARD, DataType.JSON, new ConfiguracaoDashboardDto(), "Configuração do dashboard");

    final TipoDominioParametro dominio;
    final DataType dataType;
    final String descricao;
    final Object valorPadrao;

    TipoParametroConfiguracao(TipoDominioParametro dominioIn, DataType dTypeIn, Object valorPadraoIn,
        String descricaoIn) {
        this.dominio = dominioIn;
        this.descricao = descricaoIn;
        this.dataType = dTypeIn;
        this.valorPadrao = valorPadraoIn;
    }


    @Override
    public String getDomain() {
        return getDominio().name();
    }

    @Override
    public Object getDefaultValue() {
        return valorPadrao;
    }

    @Override
    public Map<String, String> getDescriptions() {
        return Map.of();
    }

}
