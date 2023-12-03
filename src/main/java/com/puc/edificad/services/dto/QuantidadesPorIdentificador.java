package com.puc.edificad.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantidadesPorIdentificador {
    private String identificador;
    private Integer quantidade;

}
