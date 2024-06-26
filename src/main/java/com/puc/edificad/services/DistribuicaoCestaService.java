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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;


public interface DistribuicaoCestaService extends BaseService<DistribuicaoCesta> {

    DistribuicaoCestaDto save(DistribuicaoCestaDto dto);

    void update(DistribuicaoCestaDto dto);

    List<DistribuicaoCestaDto> findAllDto();

    List<DistribuicaoCestaDto> findBy(String cesta, String cpfBeneficiario, String cpfVoluntario, LocalDate data);

    ResumoDistribuicaoCestaDto obterResumoDeDistribuicaoCestas(LocalDateTime dataInicioReferencia,
                                                               LocalDateTime dataFimReferencia);

    List<QuantidadesPorAnoMes> obterQuantidadesBeneficiariosAssistidosPorMesAno(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia);

    List<QuantidadesPorAnoMes> obterQuantidadeCestasDistribuidasPorPeriodo(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia);

    List<DistribuicaoCestaPorPeriodo> obterDistribuicaoPorPeriodo(LocalDate inicio, LocalDate fim,
        Cesta cesta, Beneficiario beneficiario, Voluntario voluntario);

    void cancelarDistribuicaoCesta(Long idDistribuicaoCesta, String motivoCancelamento);

    Page<DistribuicaoCesta> obterDistribuicaoCestasNaoCanceladas(String searchValue, Pageable pageable);
}
