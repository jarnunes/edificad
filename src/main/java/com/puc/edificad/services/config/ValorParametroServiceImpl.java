package com.puc.edificad.services.config;

import com.jnunes.spgcore.commons.context.StaticContextAccessor;
import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.model.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ValorParametroServiceImpl extends BaseServiceImpl<ValorParametro> implements ValorParametroService {

    ValorParametroRepository valorParametroRepository;

    @Autowired
    void setParametroLogicoRepository(ValorParametroRepository valorParametroRepository) {
        this.valorParametroRepository = valorParametroRepository;
    }

    private void validarTipoValorParametro(TipoParametroConfiguracao parametro, TipoDataTypeParametro dataTypeEsperado) {
        ValidationUtils.validate(parametro.getDType() != null && parametro.getDType().equals(dataTypeEsperado),
                "tipo.valor.parametro.invalido", parametro.name());
    }

    @Override
    public ValorParametroLogico obterValorParametroLogico(TipoParametroConfiguracao parametro) {
        validarTipoValorParametro(parametro, TipoDataTypeParametro.BOOLEAN);
        return valorParametroRepository.findFirstByParametro_Nome(parametro)
                .map(ValorParametroLogico.class::cast).orElse(null);
    }

    @Override
    public ValorParametroNumerico obterValorParametroNumerico(TipoParametroConfiguracao parametro) {
        validarTipoValorParametro(parametro, TipoDataTypeParametro.NUMERIC);
        return valorParametroRepository.findFirstByParametro_Nome(parametro)
                .map(ValorParametroNumerico.class::cast).orElse(null);
    }

    @Override
    public ValorParametroJson obterValorParametroJson(TipoParametroConfiguracao parametro) {
        validarTipoValorParametro(parametro, TipoDataTypeParametro.JSON);
        return valorParametroRepository.findFirstByParametro_Nome(parametro)
            .map(ValorParametroJson.class::cast).orElse(null);
    }

}
