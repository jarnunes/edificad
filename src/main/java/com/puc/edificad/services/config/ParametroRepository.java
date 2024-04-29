package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseRepository;
import com.puc.edificad.model.config.Parametro;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametroRepository extends BaseRepository<Parametro> {

    Optional<Parametro> findFirstByNomeEquals(@Param("nome") TipoParametroConfiguracao nome);
}
