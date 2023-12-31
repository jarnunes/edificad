package com.puc.edificad.services;

import com.jnunes.spgcore.service.BaseRepository;
import com.puc.edificad.model.Voluntario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioRepository extends BaseRepository<Voluntario> {

    @Query("from Voluntario vo where "
            + " (:nomeVoluntario is null or vo.nome ilike concat('%', cast(:nomeVoluntario as string) , '%') ) ")
    List<Voluntario> findByNome(String nomeVoluntario);

}
