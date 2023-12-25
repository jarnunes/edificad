package com.puc.edificad.commons.datatypes;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TipoBoolean {
    SIM("Sim", true),
    NAO("Não", false);
    private final String label;
    private final Boolean value;

    TipoBoolean(String label, Boolean value) {
        this.label = label;
        this.value = value;
    }

    public static boolean of(String label) {
        return Arrays.stream(TipoBoolean.values()).filter(it -> it.label.equals(label))
            .findFirst().map(it -> it.value).orElse(false);
    }

}
