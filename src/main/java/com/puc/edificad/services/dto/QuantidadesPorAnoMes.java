package com.puc.edificad.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
public class QuantidadesPorAnoMes {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Year ano;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Month mes;

    private Long quantidade;

    public QuantidadesPorAnoMes(Object yearIn, Object monthIn, Object quantidade) {
        setAno(Year.of((Integer) yearIn));
        setMes(Month.of((Integer) monthIn));
        setQuantidade((Long) quantidade);
    }
}
