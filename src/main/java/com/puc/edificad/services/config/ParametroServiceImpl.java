package com.puc.edificad.services.config;

import com.jnunes.spgcore.commons.utils.JsonUtils;
import com.jnunes.spgcore.services.BaseServiceImpl;
import com.jnunes.spgparameter.model.Parameter;
import com.jnunes.spgparameter.model.ParameterValueBoolean;
import com.jnunes.spgparameter.model.ParameterValueJson;
import com.jnunes.spgparameter.services.ParameterValueRepository;
import com.jnunes.spgparameter.services.ParameterValueService;
import com.puc.edificad.commons.utils.BooUtils;
import com.puc.edificad.model.config.TipoParametroConfiguracao;
import com.puc.edificad.model.dto.ConfiguracaoDashboardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.puc.edificad.model.config.TipoParametroConfiguracao.*;

@Service
@Transactional
public class ParametroServiceImpl extends BaseServiceImpl<Parameter> implements ParametroService {

    private ParameterValueService valorParametroService;
    private ParameterValueRepository repository;

    @Autowired
    void setParametroLogicoRepository(ParameterValueService valorParametroService) {
        this.valorParametroService = valorParametroService;
    }

    @Autowired
    void setRepository(ParameterValueRepository repository){
        this.repository = repository;
    }

    @Override
    public Parameter save(Parameter entity) {
        super.save(entity);
        entity.getParameterValues().forEach(valorParametro -> {
            valorParametro.setParameter(entity);
            valorParametroService.save(valorParametro);
        });

        return entity;
    }


    private boolean obterValorParametroLogicoOuDefault(TipoParametroConfiguracao parametro) {
        ParameterValueBoolean logico = valorParametroService.getLogicParameterValue(parametro);
        return Optional.ofNullable(logico).map(ParameterValueBoolean::getValue)
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

    @Override
    public ConfiguracaoDashboardDto obterConfiguracaoDashboard() {
        ParameterValueJson valorParametro = valorParametroService.getJsonParameterValue(CONFIGURACAO_DASHBOARD);

        if(valorParametro == null)
            return new ConfiguracaoDashboardDto();

        return JsonUtils.toObject(valorParametro.getValue(), ConfiguracaoDashboardDto.class);
    }
}
