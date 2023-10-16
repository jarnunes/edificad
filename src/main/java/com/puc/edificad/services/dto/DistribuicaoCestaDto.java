package com.puc.edificad.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DistribuicaoCestaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4275329005901651001L;

    @JsonProperty(required = true)
    private Long idVoluntario;

    @JsonProperty(required = true)
    private Long idBeneficiario;

    @JsonProperty(required = true)
    private Long idCesta;

    @JsonProperty(required = true)
    private LocalDateTime dataHora;
}
