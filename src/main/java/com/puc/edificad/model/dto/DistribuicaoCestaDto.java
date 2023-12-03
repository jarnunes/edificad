package com.puc.edificad.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DistribuicaoCestaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4275329005901651001L;

    private Long id;

    @JsonProperty(required = true)
    private VoluntarioDto voluntario;

    @JsonProperty(required = true)
    private BeneficiarioDto beneficiario;

    @JsonProperty(required = true)
    private CestaDto cesta;

    @JsonProperty(required = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;
}
