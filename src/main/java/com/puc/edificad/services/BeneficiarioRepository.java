package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseRepository;
import com.puc.edificad.model.Beneficiario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiarioRepository extends BaseRepository<Beneficiario> {

    @Query("from Beneficiario b where "
            + "     (:nome is null or b.nome ilike concat('%', cast(:nome as string), '%')) "
            + " and (:cpf is null or b.cpf = :cpf) "
            + " order by b.id limit 10 ")
    List<Beneficiario> findByNomeCpf(String nome, String cpf);
}
