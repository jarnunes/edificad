package com.puc.edificad.web.controller.distribuicao_cesta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelarDistribuicaoRequest {

    @JsonProperty(required = true)
    String motivoCancelamento;

    @JsonProperty(required = true)
    List<Long> idsSelecionados = new ArrayList<>();
}
