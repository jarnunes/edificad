package com.puc.edificad.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResumoDistribuicaoCestaDto {
    private Long cestasDistribuidas;
    private Long beneficiariosAssistidos;
    private Long quantidadeEstoque;

}
