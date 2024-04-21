package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseRepository;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.config.ValorParametro;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValorParametroLogicoRepository extends BaseRepository<ValorParametro> {

    Optional<ValorParametro> findFirstByParametro_Nome(@Param("parametro") TipoParametroConfiguracao parametro);
}
