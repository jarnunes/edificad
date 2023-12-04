package com.puc.edificad.web.controller.relatorios;

import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.Voluntario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ConsultaPorPeriodoForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fim;
    private Cesta cesta;
    private Beneficiario beneficiario;
    private Voluntario voluntario;
}
