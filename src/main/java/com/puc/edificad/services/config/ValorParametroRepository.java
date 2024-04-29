package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseRepository;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.config.ValorParametro;
import com.puc.edificad.model.config.ValorParametroLogico;
import com.puc.edificad.model.config.ValorParametroNumerico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValorParametroRepository extends BaseRepository<ValorParametro> {

    Optional<ValorParametro> findFirstByParametro_Nome(@Param("parametro") TipoParametroConfiguracao parametro);

    @Query("from ValorParametroLogico vpl " +
            " where vpl.parametro.nome = : parametro")
    List<ValorParametroLogico> obterValoresParametroLogicoPorNome(String parametro);

    @Query("from ValorParametroNumerico vpn " +
            " where vpn.parametro.nome = : parametro")
    List<ValorParametroNumerico> obterValoresParametroNumericoPorNome(String parametro);

}
