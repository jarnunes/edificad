package com.puc.edificad.services;

import com.puc.edificad.model.config.*;
import com.puc.edificad.services.config.ParametroService;
import com.puc.edificad.services.config.ValorParametroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ParametroTest {

    @Autowired
    private ParametroService service;

    @Autowired
    private ValorParametroRepository repository;

    @Test
    void create() {
        Parametro parametro = new Parametro();
        parametro.setNome(TipoParametroConfiguracao.CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA);
        parametro.setDominio(TipoDominioParametro.CESTA);
        parametro.setDType(TipoDataTypeParametro.BOOLEAN);

        ValorParametroLogico vpl = new ValorParametroLogico();
        vpl.setValor(false);
        parametro.getValoresParametro().add(vpl);

        service.save(parametro);
        assertNotNull(vpl);
    }

    @Test
    void create_valorParametro() {
        ValorParametroLogico vpl = new ValorParametroLogico();
        vpl.setValor(true);
        TipoParametroConfiguracao.CONTABILIZAR_ESTOQUE_DEPOIS_CANCELAMENTO_DISTRIBUICAO_CESTA.save(vpl);

        assertNotNull(vpl.getId());
    }

    @Test
    void findAll() {
        //ValorParametroLogico.class.cast(storedEntities.get(0))
        List<ValorParametro> storedEntities = repository.findAll();
        assertFalse(storedEntities.isEmpty());
    }

}
