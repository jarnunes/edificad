package com.puc.edificad.commons.datatypes;

import com.jnunes.spgcore.commons.LabelMessage;
import lombok.Getter;

@Getter
public enum DataTypeBoolean implements LabelMessage {
    YES(true),
    NO(false);

    private final Boolean value;

    DataTypeBoolean(Boolean value) {
        this.value = value;
    }

}
