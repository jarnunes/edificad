package com.puc.edificad.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DistribuicaoCestaPorPeriodo {
    private LocalDateTime data;
    private String cesta;
    private String beneficiario;
    private String cpfBeneficiario;
    private String voluntario;
    private String cpfVoluntario;

    public DistribuicaoCestaPorPeriodo(LocalDateTime data, String cesta, String beneficiario, String cpfBeneficiario,
        String voluntario, String cpfVoluntario) {
        this.data = data;
        this.cesta = cesta;
        this.beneficiario = beneficiario;
        this.cpfBeneficiario = cpfBeneficiario;
        this.voluntario = voluntario;
        this.cpfVoluntario = cpfVoluntario;
    }
}
