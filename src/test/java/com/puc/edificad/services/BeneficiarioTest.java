package com.puc.edificad.services;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.model.Beneficiario;
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
class BeneficiarioTest {

    @Autowired
    private BeneficiarioService service;

    @Test
    void create() {
        Beneficiario entity = ObjMotherBeneficiario.criar();

        service.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    void update() {
        Beneficiario entity = ObjMotherBeneficiario.criar();
        service.save(entity);

        entity.setNome("BENEFICIARIO 1033");
        entity.setCpf("020.202.894-08");
        entity.setEmail("voluntario1033@gmail.com");
        entity.setTelefone("+5531992294567");
        service.update(entity);

        Beneficiario storedEntity = service.findById(entity.getId())
                .orElseThrow(EntityNotFoundException::notFound);

        assertEquals(entity, storedEntity);
    }

    @Test
    void delete() {
        Beneficiario entity = ObjMotherBeneficiario.criar();
        service.save(entity);
        service.delete(entity);

        Beneficiario storedEntity = service.findById(entity.getId()).orElse(null);
        assertNull(storedEntity);
    }

    @Test
    void findAll() {
        List<Beneficiario> entities = ObjMotherBeneficiario.criarLista5Entidades();
        entities.forEach(service::save);

        List<Beneficiario> storedEntities = service.findAll();
        assertTrue(storedEntities.containsAll(entities));
    }

}
