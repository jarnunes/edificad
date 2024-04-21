package com.puc.edificad.model.config;

import com.jnunes.spgcore.commons.LabelMessage;
import lombok.Getter;

import static com.puc.edificad.model.config.TipoDataTypeParametro.BOOLEAN;
import static com.puc.edificad.model.config.TipoDominioParametro.CESTA;
import static com.puc.edificad.model.config.TipoDominioParametro.DISTRIBUICAO_CESTA;

@Getter
public enum TipoParametroConfiguracao implements LabelMessage {

    PERMITIR_CANCELAMENTO_DISTRIBUICAO_CESTA(DISTRIBUICAO_CESTA, BOOLEAN, false,
        "Indica se pode ser cancelada distribuição de cestas"),
    CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA(CESTA, BOOLEAN, true,
        "Indica se a quantidade de cestas em estoque será incrementada após cancelar distribuição");

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

}
