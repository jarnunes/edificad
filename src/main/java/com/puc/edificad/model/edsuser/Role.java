package com.puc.edificad.model.edsuser;

import lombok.Getter;
@Getter
public enum Role {
    ADMIN("Administrador"), OPERATOR("Operador"), WEBSERVICES("WebServices");

    private final String value;
    Role(String valueIn){
        this.value = valueIn;
    }

    public static final String RL_ADMIN = ADMIN.name();
    public static final String RL_OPERATOR = OPERATOR.name();
    public static final String RL_WEBSERVICES = WEBSERVICES.name();
}
