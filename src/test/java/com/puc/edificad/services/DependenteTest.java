package com.puc.edificad.services;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.object_mother.ObjMotherDependente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DependenteTest {

    @Autowired
    private DependenteService service;

    @Test
    public void create() {
        Dependente entity = ObjMotherDependente.criar();

        service.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    public void update() {
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
    public void delete() {
        Dependente entity = ObjMotherDependente.criar();
        service.save(entity);
        service.delete(entity);

        Dependente storedEntity = service.findById(entity.getId()).orElse(null);
        assertNull(storedEntity);
    }

    @Test
    public void findAll() {
        List<Dependente> entities = ObjMotherDependente.criarLista();
        entities.forEach(service::save);

        List<Dependente> storedEntities = service.findAll();
        assertTrue(storedEntities.containsAll(entities));
    }

}
