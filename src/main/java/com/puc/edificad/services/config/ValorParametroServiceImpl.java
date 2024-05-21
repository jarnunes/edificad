package com.puc.edificad.services.config;

import com.jnunes.spgcore.commons.utils.ValidationUtils;
import com.jnunes.spgcore.services.BaseServiceImpl;
import com.puc.edificad.model.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ValorParametroServiceImpl extends BaseServiceImpl<ValorParametro> implements ValorParametroService {

    ParametroRepository parametroRepository;
    ValorParametroRepository valorParametroRepository;
    ValorParametroLogicoRepository valorParametroLogicoRepository;
    ValorParametroNumericoRepository valorParametroNumericoRepository;
    ValorParametroJsonRepository valorParametroJsonRepository;

    @Autowired
    void setParametroRepository(ParametroRepository parametroRepository) {
        this.parametroRepository = parametroRepository;
    }

    @Autowired
    void setParametroLogicoRepository(ValorParametroRepository valorParametroRepository) {
        this.valorParametroRepository = valorParametroRepository;
    }

    @Autowired
    void setParametroLogicoRepository(ValorParametroLogicoRepository valorParametroLogicoRepository) {
        this.valorParametroLogicoRepository = valorParametroLogicoRepository;
    }

    @Autowired
    void setParametroLogicoRepository(ValorParametroNumericoRepository valorParametroNumericoRepository) {
        this.valorParametroNumericoRepository = valorParametroNumericoRepository;
    }

    @Autowired
    void setParametroLogicoRepository(ValorParametroJsonRepository valorParametroJsonRepository) {
        this.valorParametroJsonRepository = valorParametroJsonRepository;
    }

    @Override
    public <T extends ValorParametro> void saveByType(T entity) {
        ValidationUtils.validate(entity.getParametro() != null,
                "valor.parametro.service.parametro.obrigatorio");

        Parametro parametro = entity.getParametro();
        if (parametro.getId() == null)
            parametroRepository.save(parametro);

        if (entity instanceof ValorParametroLogico valorParametroLogico) {
            valorParametroLogicoRepository.save(valorParametroLogico);
        } else if (entity instanceof ValorParametroNumerico valorParametroNumerico) {
            valorParametroNumericoRepository.save(valorParametroNumerico);
        } else if (entity instanceof ValorParametroJson valorParametroJson) {
            valorParametroJsonRepository.save(valorParametroJson);
        }
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
