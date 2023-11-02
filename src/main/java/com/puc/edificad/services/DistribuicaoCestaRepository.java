package com.puc.edificad.services;

import com.puc.edificad.model.DistribuicaoCesta;
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
    List<DistribuicaoCesta> findByCestaBeneficiarioVoluntarioData(String cesta, String cpfBeneficiario, String cpfVoluntario,
                      LocalDate data);
}
