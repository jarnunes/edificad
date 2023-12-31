package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseRepository;
import com.puc.edificad.model.Cesta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CestaRepository extends BaseRepository<Cesta> {

    @Query("from Cesta  c where "
            + " (:id is null or c.id = :id)"
            + " and (:nome is null or upper(c.nome) like concat('%', upper(:nome), '%'))" )
    List<Cesta> findByIdNome(Long id, String nome);
}
