package com.puc.edificad.services;

import com.puc.edificad.model.Voluntario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioRepository extends BaseRepository<Voluntario> {

    @Query("from Voluntario  v where"
        + " (:id is null or v.id = :id ) "
        + " and (:nome is null or upper(v.nome) like concat('%', upper(:nome), '%')) "
        + " and (:cpf is null or v.cpf = :cpf)")
    List<Voluntario> findByIdNomeCpf(Long id, String nome, String cpf);

}
