package com.puc.edificad.services;

import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.services.dto.QuantidadesPorAnoMes;
import com.puc.edificad.services.dto.ResumoDistribuicaoCestaDto;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;


public interface DistribuicaoCestaService extends BaseService<DistribuicaoCesta> {

    DistribuicaoCestaDto save(DistribuicaoCestaDto dto);

    void update(DistribuicaoCestaDto dto);

    List<DistribuicaoCestaDto> findAllDto();

    List<DistribuicaoCestaDto> findBy(String cesta, String cpfBeneficiario, String cpfVoluntario, LocalDate data);

    ResumoDistribuicaoCestaDto obterResumoDeDistribuicaoCestas();

    List<QuantidadesPorAnoMes> obterQuantidadesBeneficiariosAssistidosPorMesAno(Year anoReferencia,
        Month mesReferencia, Long qtdMesesAnteriores);

    List<QuantidadesPorAnoMes> obterQuantidadeCestasDistribuidasPorMesAno(Year anoReferencia,
        Month mesReferencia, Long qtdMesesAnteriores);


}
