package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseRepository;
import com.puc.edificad.model.config.Configuracao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracaoRepository extends BaseRepository<Configuracao> {

    @Query("from Configuracao c where c.id in (select max(conf.id) from Configuracao conf)")
    Optional<Configuracao> findFirstConfiguracao();
}
