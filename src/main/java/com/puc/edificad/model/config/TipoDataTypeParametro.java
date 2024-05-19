package com.puc.edificad.model.config;

import lombok.Getter;

@Getter
public enum TipoDataTypeParametro {

    BOOLEAN(Boolean.class),
    NUMERIC(Integer.class),
    JSON(String.class);

    final Class<?> type;

    TipoDataTypeParametro(Class<?> typeIn) {
        this.type = typeIn;
    }

    public boolean isBoolean() {
        return this.equals(BOOLEAN);
    }

    public boolean isNumeric() {
        return this.equals(NUMERIC);
    }

    public boolean isJson() {
        return this.equals(JSON);
    }
}
