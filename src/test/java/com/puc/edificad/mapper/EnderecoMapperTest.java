package com.puc.edificad.mapper;

import com.puc.edificad.commons.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Endereco;
import com.puc.edificad.model.dto.BeneficiarioDto;
import com.puc.edificad.model.dto.EnderecoDto;
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
class EnderecoMapperTest {

    @Autowired
    private BeneficiarioService service;

    @Autowired
    private BeneficiarioMapper beneficiarioMapper;
    
    @Autowired
    private EnderecoMapper enderecoMapper;


    @Test
    void wenCopyEntityToDto_thenReturnsObjectWithAllAttributes() {
        Beneficiario entity = ObjMotherBeneficiario.criar();
        service.save(entity);

        EnderecoDto dto = enderecoMapper.toDto(entity);
        assertEqualsProperties(entity.getEndereco(), dto);
    }

    @Test
    void wenDtoToEntity_thenReturnsObjectWithAllAttributes() {
        Beneficiario entity = ObjMotherBeneficiario.criar();
        service.save(entity);

        BeneficiarioDto dto = beneficiarioMapper.toDto(entity);

        Endereco entityEndereco = enderecoMapper.toEntity(dto);
        assertEqualsProperties(entityEndereco, dto.getEndereco());
    }


    private void assertEqualsProperties(Endereco source, EnderecoDto target){
            assertEquals(source.getId(), target.getId());
            assertEquals(source.getLogradouro(), target.getLogradouro());
            assertEquals(source.getNumero(), target.getNumero());
            assertEquals(source.getCep(), target.getCep());
            assertEquals(source.getBairro(), target.getBairro());
            assertEquals(source.getCidade(), target.getCidade());
            assertEquals(source.getEstado(), target.getEstado());
    }
}
