package com.puc.edificad.services;

import com.jnunes.spgcore.services.BaseRepository;
import com.puc.edificad.model.Voluntario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioRepository extends BaseRepository<Voluntario> {

    @Query("from Voluntario vo where "
        + " (:nomeVoluntario is null or vo.nome ilike concat('%', cast(:nomeVoluntario as string) , '%') ) "
        + " order by vo.id limit 10 ")
    List<Voluntario> findByNome(String nomeVoluntario);

}
