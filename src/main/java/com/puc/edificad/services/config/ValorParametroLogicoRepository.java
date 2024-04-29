package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseRepository;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.config.ValorParametroLogico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValorParametroLogicoRepository extends BaseRepository<ValorParametroLogico> {

    @Query("from ValorParametroLogico vpl " +
            " where vpl.parametro.nome = :nomeParametro " +
            " order by vpl.id limit 1")
    Optional<ValorParametroLogico> obterValorParametroPorNomeParametro(TipoParametroConfiguracao nomeParametro);
}
