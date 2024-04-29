package com.puc.edificad.services.config;

import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.commons.utils.BooUtils;
import com.puc.edificad.model.config.Parametro;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.config.ValorParametroLogico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.puc.edificad.model.config.TipoParametroConfiguracao.CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA;
import static com.puc.edificad.model.config.TipoParametroConfiguracao.PERMITIR_CANCELAMENTO_DISTRIBUICAO_CESTA;

@Service
@Transactional
public class ParametroServiceImpl extends BaseServiceImpl<Parametro> implements ParametroService {

    private ValorParametroService valorParametroService;
    private ParametroRepository repository;

    @Autowired
    void setParametroLogicoRepository(ValorParametroService valorParametroService) {
        this.valorParametroService = valorParametroService;
    }

    @Autowired
    void setRepository(ParametroRepository repository){
        this.repository = repository;
    }

    @Override
    public Parametro save(Parametro entity) {
        super.save(entity);
        entity.getValoresParametro().forEach(valorParametro -> {
            valorParametro.setParametro(entity);
            valorParametroService.save(valorParametro);
        });

        return entity;
    }

    @Override
    public Parametro obterParametroPorNome(TipoParametroConfiguracao nome) {
        return repository.findFirstByNomeEquals(nome).orElse(null);
    }

    private boolean obterValorParametroLogicoOuDefault(TipoParametroConfiguracao parametro) {
        ValorParametroLogico logico = valorParametroService.obterValorParametroLogico(parametro);
        return Optional.ofNullable(logico).map(ValorParametroLogico::getValor)
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
