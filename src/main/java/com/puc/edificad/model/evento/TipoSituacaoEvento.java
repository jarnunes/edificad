package com.puc.edificad.model.evento;

import lombok.Getter;

@Getter
public enum TipoSituacaoEvento {

    NOVO("Novo"),
    PENDENTE("Pendente"),
    CONSUMIDO("Consumido"),
    EXCECAO("Excecao");

    private final String value;

    TipoSituacaoEvento(String valueIn) {
        this.value = valueIn;
    }

}
