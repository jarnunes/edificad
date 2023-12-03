package com.puc.edificad.mapper;

import com.puc.edificad.commons.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.services.BeneficiarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BeneficiarioMapperTest {

    @Autowired
    private BeneficiarioService service;

    @Autowired
    private BeneficiarioMapper beneficiarioMapper;


    @Test
    void wenCopyEntityToDto_thenReturnsObjectWithAllAttributes() {
        Beneficiario entity = ObjMotherBeneficiario.criar();
        service.save(entity);

        BeneficiarioDto dto = beneficiarioMapper.toDto(entity);
        assertEqualsProperties(entity, dto);
    }

    @Test
    void wenDtoToEntity_thenReturnsObjectWithAllAttributes() {
        Beneficiario entity = ObjMotherBeneficiario.criar();
        service.save(entity);

        BeneficiarioDto dto = beneficiarioMapper.toDto(entity);

        Beneficiario mappedEntity = beneficiarioMapper.toEntity(dto);
        assertEqualsProperties(mappedEntity, dto);
    }


    private void assertEqualsProperties(Beneficiario source, BeneficiarioDto target){
        assertEquals(source.getId(), target.getId());
        assertEquals(source.getNome(), target.getNome());
        assertEquals(source.getEmail(), target.getEmail());
        assertEquals(source.getTelefone(), target.getTelefone());
        assertEquals(source.getCpf(), target.getCpf());
        assertEquals(source.getDataNascimento(), target.getDataNascimento());
    }
}
