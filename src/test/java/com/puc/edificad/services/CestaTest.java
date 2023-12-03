package com.puc.edificad.services;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.object_mother.ObjMotherCesta;
import com.puc.edificad.model.Cesta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CestaTest {

    @Autowired
    private CestaService cestaService;

    @Test
    void create() {
        Cesta cesta = ObjMotherCesta.criar();

        cestaService.save(cesta);
        assertNotNull(cesta.getId());
    }

    @Test
    void update() {
        Cesta cesta = ObjMotherCesta.criar();
        cestaService.save(cesta);

        final String novoNome = "CESTA 02";
        final String novaDescricao = "Cesta para familias yyy";
        final Integer novaQuantidade = 100;

        cesta.setNome(novoNome);
        cesta.setDescricao(novaDescricao);
        cesta.setQuantidadeEstoque(novaQuantidade);
        cestaService.update(cesta);

        Cesta storedEntity = cestaService.findById(cesta.getId())
                .orElseThrow(EntityNotFoundException::notFound);

        assertEquals(cesta, storedEntity);
    }

    @Test
    void delete() {
        Cesta cesta = ObjMotherCesta.criar();
        cestaService.save(cesta);
        cestaService.delete(cesta);

        Cesta storedCesta = cestaService.findById(cesta.getId()).orElse(null);
        assertNull(storedCesta);
    }

    @Test
    void findAll() {
        List<Cesta> lista = ObjMotherCesta.criarLista5Entidades();
        lista.forEach(cestaService::save);

        List<Cesta> storedEntities = cestaService.findAll();
        assertTrue(storedEntities.containsAll(lista));
    }

}
