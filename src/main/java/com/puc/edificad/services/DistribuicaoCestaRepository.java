package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseRepository;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.services.dto.DistribuicaoCestaPorPeriodo;
import com.puc.edificad.services.dto.QuantidadesPorAnoMes;
import com.puc.edificad.services.dto.ResumoDistribuicaoCestaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DistribuicaoCestaRepository extends BaseRepository<DistribuicaoCesta> {

    @Query("from DistribuicaoCesta dc join dc.voluntario v join dc.beneficiario b join dc.cesta c where "
            + " (:cesta is null or c.nome = :cesta) "
            + " and (:cpfBeneficiario is null or b.cpf = :cpfBeneficiario) "
            + " and (:cpfVoluntario is null or v.cpf = :cpfVoluntario) "
            + "        and (cast(:data as date) is null or cast(dc.dataHora as date) = :data )")
    List<DistribuicaoCesta> findByCestaBeneficiarioVoluntarioData(String cesta, String cpfBeneficiario,
        String cpfVoluntario, LocalDate data);

    @Query("select new com.puc.edificad.services.dto.ResumoDistribuicaoCestaDto( "
        + "     count(dc.id), "
        + "     count(distinct dc.beneficiario.id)"
        + " ) "
        + " from DistribuicaoCesta dc "
        + " where dc.cancelamento is null "
        + "     and dc.dataHora >= :dataInicioReferencia "
        + "     and dc.dataHora < :dataFimReferencia " )
    ResumoDistribuicaoCestaDto obterResumoDeDistribuicaoCestas(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia);

    @Query("select new com.puc.edificad.services.dto.QuantidadesPorAnoMes( "
        + "     extract(YEAR from dc.dataHora), "
        + "     extract(MONTH from dc.dataHora), "
        + "     count(dc.id)) "
        + " from    DistribuicaoCesta dc "
        + " where dc.cancelamento is null "
        + " and dc.dataHora >= :dataInicioReferencia "
        + " and dc.dataHora < :dataFimReferencia "
        + " group by 1, 2 " )
    List<QuantidadesPorAnoMes> obterQuantidadesBeneficiariosAssistidosEmUmPeriodo(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia);


    @Query("select new com.puc.edificad.services.dto.QuantidadesPorAnoMes( "
            + "     year (dc.dataHora), "
            + "     month(dc.dataHora), "
            + "     count(dc.beneficiario.id)) "
            + " from    DistribuicaoCesta dc "
            + " where dc.cancelamento is null "
            + "   and dc.dataHora >= :dataInicioReferencia "
            + "   and dc.dataHora <= :dataFimReferencia "
            + " group by 1, 2 " )
    List<QuantidadesPorAnoMes> obterQuantidadesCestasDistribuidasEmUmPeriodo(LocalDateTime dataInicioReferencia,
        LocalDateTime dataFimReferencia);

    @Query("select new com.puc.edificad.services.dto.DistribuicaoCestaPorPeriodo("
        + " dc.dataHora, ct.nome, bc.nome, bc.cpf, vl.nome, vl.cpf )"
        + " from DistribuicaoCesta dc "
        + "     join dc.cesta as ct "
        + "     join dc.beneficiario bc "
        + "     join dc.voluntario vl "
        + " where dc.cancelamento is null  "
        + "   and dc.dataHora >= :inicio "
        + "   and dc.dataHora <= :fim "
        + "   and (:cesta is null or ct = :cesta ) "
        + "   and (:beneficiario is null or bc = :beneficiario ) "
        + "   and (:voluntario is null or vl = :voluntario ) "
        + " order by dc.dataHora ")
    List<DistribuicaoCestaPorPeriodo> obterDistribuicaoPorPeriodo(LocalDateTime inicio, LocalDateTime fim, Cesta cesta,
        Beneficiario beneficiario, Voluntario voluntario);

    @Query("from DistribuicaoCesta dc where dc.cancelamento is null" +
            " and (:searchValue is null " +
            "       or (lower(dc.beneficiario.nome) ilike lower(concat('%', cast(:searchValue as string), '%'))) " +
            "       or (lower(dc.beneficiario.cpf) ilike lower(concat('%', cast(:searchValue as string), '%'))) " +
            "       or (lower(dc.voluntario.nome) ilike lower(concat('%', cast(:searchValue as string), '%'))) " +
            "       or (lower(dc.voluntario.cpf) ilike lower(concat('%', cast(:searchValue as string), '%'))) " +
            "       or (lower(dc.cesta.nome) ilike lower(concat('%', cast(:searchValue as string), '%'))) " +
            "       or (lower(cast(dc.dataHora as string)) ilike lower(concat('%', cast(:searchValue as string), '%'))) " +
            "     )")
    Page<DistribuicaoCesta> obterDistribuicaoCestasNaoCanceladas(String searchValue, Pageable pageable);
}
