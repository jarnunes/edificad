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
    private Integer ano;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer mes;

    private Long quantidade;

    public QuantidadesPorAnoMes(Integer yearIn, Integer monthIn, Object quantidade) {
        setAno(yearIn);
        setMes(monthIn);
        setQuantidade((Long) quantidade);
    }
}
