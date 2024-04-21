package com.puc.edificad.model.config;

public enum TipoDataTypeParametro {

    BOOLEAN(Boolean.class);

    final Class<?> dType;

    TipoDataTypeParametro(Class<?> dType) {
        this.dType = dType;
    }
}
