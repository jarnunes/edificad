package com.puc.edificad.services;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.object_mother.ObjMotherVoluntario;
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
public class VoluntarioTest {

    @Autowired
    private VoluntarioService service;

    @Test
    public void create() {
        Voluntario entity = ObjMotherVoluntario.criar();

        service.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    public void update() {
        Voluntario entity = ObjMotherVoluntario.criar();
        service.save(entity);

        entity.setNome("VOLUNTARIO 1033");
        entity.setCpf("020.202.894-08");
        entity.setEmail("voluntario1033@gmail.com");
        entity.setTelefone("+5531992294567");
        service.update(entity);

        Voluntario storedEntity = service.findById(entity.getId())
                .orElseThrow(EntityNotFoundException::notFound);

        assertEquals(entity, storedEntity);
    }

    @Test
    public void delete() {
        Voluntario entity = ObjMotherVoluntario.criar();
        service.save(entity);
        service.delete(entity);

        Voluntario storedEntity = service.findById(entity.getId()).orElse(null);
        assertNull(storedEntity);
    }

    @Test
    public void findAll() {
        List<Voluntario> entities = ObjMotherVoluntario.criarLista();
        entities.forEach(service::save);

        List<Voluntario> storedEntities = service.findAll();
        assertTrue(storedEntities.containsAll(entities));
    }

}
