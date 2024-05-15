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
        return this.type.equals(Boolean.class);
    }

    public boolean isNumeric() {
        return this.type.equals(Integer.class);
    }

}
