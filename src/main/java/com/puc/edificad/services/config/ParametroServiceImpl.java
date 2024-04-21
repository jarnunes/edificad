package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.commons.utils.BooUtils;
import com.puc.edificad.model.config.Parametro;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.config.ValorParametro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.puc.edificad.model.config.TipoParametroConfiguracao.CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA;
import static com.puc.edificad.model.config.TipoParametroConfiguracao.PERMITIR_CANCELAMENTO_DISTRIBUICAO_CESTA;

@Service
@Transactional
public class ParametroServiceImpl extends BaseServiceImpl<Parametro> implements ParametroService {

    private ValorParametroLogicoRepository parametroLogicoRepository;

    @Autowired
    void setParametroLogicoRepository(ValorParametroLogicoRepository parametroLogicoRepository) {
        this.parametroLogicoRepository = parametroLogicoRepository;
    }

    @Override
    public Parametro save(Parametro entity) {
        return super.save(entity);
    }

    private boolean obterValorParametroLogicoOuDefault(TipoParametroConfiguracao parametro) {
        return parametroLogicoRepository.findFirstByParametro_Nome(parametro).map(ValorParametro::getValor)
                .orElseGet(() -> BooUtils.toBoolean(parametro.getValorPadrao()));
    }

    @Override
    public boolean permitirCancelamentoDistribuicaoCesta() {
        return obterValorParametroLogicoOuDefault(PERMITIR_CANCELAMENTO_DISTRIBUICAO_CESTA);
    }

    @Override
    public boolean contabilizarEstoqueDepoisCancelamentoDistribuicaoCesta() {
        return obterValorParametroLogicoOuDefault(CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA);
    }

}
