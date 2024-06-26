package com.puc.edificad.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ConfiguracaoDashboardDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4080071506119513659L;
    private Integer periodoMaximoExibicaoPermitidoEmMeses;
    private Integer periodoExibicaoEmMeses;

    public ConfiguracaoDashboardDto() {
        this.periodoMaximoExibicaoPermitidoEmMeses = 6;
        this.periodoExibicaoEmMeses = 3;
    }
}
