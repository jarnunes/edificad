package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseRepository;
import com.puc.edificad.model.Cesta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CestaRepository extends BaseRepository<Cesta> {

    @Query("from Cesta c "
        + " where (:nome is null or c.nome ilike concat('%', cast(:nome as string) , '%')) "
        + " order by c.id limit 10 ")
    List<Cesta> findByNome(String nome);
}
