package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseService;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.services.dto.DistribuicaoCestaPorPeriodo;
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

    List<DistribuicaoCestaPorPeriodo> obterDistribuicaoPorPeriodo(LocalDate inicio, LocalDate fim,
        Cesta cesta, Beneficiario beneficiario, Voluntario voluntario);

    void cancelarDistribuicaoCesta(Long idDistribuicaoCesta, String motivoCancelamento);
}
