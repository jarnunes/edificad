package com.puc.edificad.services;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.DistribuicaoCesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.model.dto.CestaDto;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.model.dto.VoluntarioDto;
import com.puc.edificad.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.object_mother.ObjMotherCesta;
import com.puc.edificad.object_mother.ObjMotherVoluntario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DistribuicaoCestaTest {

    @Autowired
    private VoluntarioService voluntarioService;
    @Autowired
    private BeneficiarioService beneficiarioService;
    @Autowired
    private CestaService cestaService;
    @Autowired
    private DistribuicaoCestaService distribuicaoCestaService;

    @Test
    public void create() {
        DistribuicaoCesta entity = criarDistribuicaoCesta();

        distribuicaoCestaService.save(entity);
        assertNotNull(entity.getId());
    }

    @Test
    public void createDto() {
        Voluntario voluntario = criarVoluntario();
        Beneficiario beneficiario = criarBeneficiario();
        Cesta cesta = criarCesta();

        DistribuicaoCestaDto entity = new DistribuicaoCestaDto();
        entity.setDataHora(LocalDateTime.now());

        VoluntarioDto voluntarioDto = new VoluntarioDto();
        voluntarioDto.setId(voluntario.getId());
        entity.setVoluntario(voluntarioDto);

        BeneficiarioDto beneficiarioDto = new BeneficiarioDto();
        beneficiarioDto.setId(beneficiario.getId());
        entity.setBeneficiario(beneficiarioDto);

        CestaDto cestaDto = new CestaDto();
        cestaDto.setId(cesta.getId());
        entity.setCesta(cestaDto);

        distribuicaoCestaService.save(entity);
        assertNotNull(entity.getId());
    }

    private DistribuicaoCesta criarDistribuicaoCesta() {
        Voluntario voluntario = criarVoluntario();
        Beneficiario beneficiario = criarBeneficiario();
        Cesta cesta = criarCesta();

        DistribuicaoCesta entity = new DistribuicaoCesta();
        entity.setDataHora(LocalDateTime.now());
        entity.setVoluntario(voluntario);
        entity.setBeneficiario(beneficiario);
        entity.setCesta(cesta);
        return entity;
    }

    private Voluntario criarVoluntario() {
        Voluntario voluntario = ObjMotherVoluntario.criar();
        voluntarioService.save(voluntario);
        return voluntario;
    }

    private Beneficiario criarBeneficiario() {
        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);
        return beneficiario;
    }

    private Cesta criarCesta() {
        Cesta cesta = ObjMotherCesta.criar();
        cestaService.save(cesta);
        return cesta;
    }

    @Test
    public void update() {
        final LocalDateTime dataHoraValidar = LocalDateTime.now().plusMinutes(20);
        DistribuicaoCesta entity = criarDistribuicaoCesta();
        entity.setDataHora(dataHoraValidar);

        distribuicaoCestaService.update(entity);

        DistribuicaoCesta storedEntity = distribuicaoCestaService.findById(entity.getId())
                .orElseThrow(EntityNotFoundException::notFound);

        assertEquals(entity, storedEntity);
    }

    @Test
    public void delete() {
        DistribuicaoCesta entity = criarDistribuicaoCesta();
        distribuicaoCestaService.save(entity);
        distribuicaoCestaService.delete(entity);

        DistribuicaoCesta storedEntity = distribuicaoCestaService.findById(entity.getId()).orElse(null);
        assertNull(storedEntity);
    }


}
