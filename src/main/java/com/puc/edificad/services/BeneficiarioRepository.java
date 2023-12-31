package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseRepository;
import com.puc.edificad.model.Beneficiario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiarioRepository extends BaseRepository<Beneficiario> {

    @Query("from Beneficiario b where "
            +" (:id is null or b.id = :id)"
            +" and (:nome is null or upper(b.nome) like concat('%', upper(:nome), '%'))"
            +" and (:cpf is null or b.cpf = :cpf)")
    List<Beneficiario> findByIdNomeCpf(Long id, String nome, String cpf);
}
