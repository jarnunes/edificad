package com.puc.edificad.model.config;

import com.jnunes.spgcore.commons.LabelMessage;
import com.jnunes.spgcore.commons.context.StaticContextAccessor;
import com.puc.edificad.services.config.ParametroRepository;
import lombok.Getter;

import static com.puc.edificad.model.config.TipoDataTypeParametro.BOOLEAN;
import static com.puc.edificad.model.config.TipoDominioParametro.*;

@Getter
public enum TipoParametroConfiguracao implements ParametroMediator, LabelMessage {

    PERMITIR_CANCELAMENTO_DISTRIBUICAO_CESTA(DISTRIBUICAO_CESTA, BOOLEAN, false,
        "Indica se pode ser cancelada distribuição de cestas"),
    CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA(CESTA, BOOLEAN, true,
        "Indica se a quantidade de cestas em estoque será incrementada após cancelar distribuição"),
    CONFIGURACAO_DASHBOARD(DASHBOARD, TipoDataTypeParametro.JSON, null, "Configuração do dashboard");
    final TipoDominioParametro dominio;
    final TipoDataTypeParametro dType;
    final String descricao;
    final Object valorPadrao;

    TipoParametroConfiguracao(TipoDominioParametro dominioIn, TipoDataTypeParametro dTypeIn, Object valorPadraoIn,
        String descricaoIn) {
        this.dominio = dominioIn;
        this.descricao = descricaoIn;
        this.dType = dTypeIn;
        this.valorPadrao = valorPadraoIn;
    }

    @Override
    public Parametro getInstance() {
        return StaticContextAccessor
            .getBean(ParametroRepository.class)
            .findFirstByNomeEquals(this)
            .orElseGet(this::getNewInstance);
    }

    @Override
    public Parametro getNewInstance() {
        Parametro parametro = new Parametro();
        parametro.setNome(this);
        parametro.setDominio(getDominio());
        parametro.setDType(getDType());
        return parametro;
    }

}
