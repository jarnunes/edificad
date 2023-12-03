package com.puc.edificad.services;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.object_mother.ObjMotherDependente;
import com.puc.edificad.model.Dependente;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DependenteTest {

    @Autowired
    private DependenteService service;

    @Test
    void create() {
        Dependente entity = ObjMotherDependente.criar();

        service.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    void update() {
        Dependente entity = ObjMotherDependente.criar();
        service.save(entity);

        entity.setNome("DEPENDENTE 1033");
        entity.setCpf("020.202.894-08");
        entity.setEmail("dependente1033@gmail.com");
        entity.setTelefone("+5531992294567");
        service.update(entity);

        Dependente storedEntity = service.findById(entity.getId())
                .orElseThrow(EntityNotFoundException::notFound);

        assertEquals(entity, storedEntity);
    }

    @Test
    void delete() {
        Dependente entity = ObjMotherDependente.criar();
        service.save(entity);
        service.delete(entity);

        Dependente storedEntity = service.findById(entity.getId()).orElse(null);
        assertNull(storedEntity);
    }

    @Test
    void findAll() {
        List<Dependente> entities = ObjMotherDependente.criarLista5Entidades();
        entities.forEach(service::save);

        List<Dependente> storedEntities = service.findAll();
        assertTrue(storedEntities.containsAll(entities));
    }

}
