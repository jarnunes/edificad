package com.puc.edificad.model.evento;

import lombok.Getter;

@Getter
public enum TipoEvento {

    NOTIFICACAO("Notificação");

    private final String value;

    TipoEvento(String valueIn) {
        this.value = valueIn;
    }

}
