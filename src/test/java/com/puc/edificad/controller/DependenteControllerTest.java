package com.puc.edificad.controller;

import com.puc.edificad.TestUtils;
import com.puc.edificad.commons.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.commons.object_mother.ObjMotherDependente;
import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.commons.utils.MessageUtils;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.dto.DependenteDto;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.DatabaseService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.puc.edificad.TestUtils.getBearerToken;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DependenteControllerTest {

    private static final String URL_TEMPLATE = "/api/dependente";
    private static final String URL_TEMPLATE_LIST = URL_TEMPLATE + "/create-from-list";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeneficiarioService beneficiarioService;
    @Autowired
    private DatabaseService databaseService;

    @Test
    void wenPost_thenReturnsCreatedEntityWithId() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        DependenteDto dto = ObjMotherDependente.criarDto();
        dto.setIdResponsavel(beneficiario.getId());

        String json = JsonUtils.toJsonString(dto);

        mockMvc.perform(post(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").value(dto.getNome()))
            .andExpect(jsonPath("$.telefone").value(dto.getTelefone()))
            .andExpect(jsonPath("$.email").value(dto.getEmail()))
            .andExpect(jsonPath("$.cpf").value(dto.getCpf()))
            .andExpect(jsonPath("$.data_nascimento").value(dto.getDataNascimento().toString()));
    }

    @Test
    void wenPostList_thenReturnsCreatedEntitiesList() throws Exception{
        databaseService.cleanDatabase();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        List<DependenteDto> entities = ObjMotherDependente.criarLista5Dtos();
        entities.forEach(entity -> entity.setIdResponsavel(beneficiario.getId()));

        String json = JsonUtils.toJsonString(entities);

        mockMvc.perform(post(URL_TEMPLATE_LIST)
                        .header(AUTHORIZATION, getBearerToken(mockMvc))
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(entities.size())))
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void wenPostDuplicatedUniqueKey_thenReturns422Error() throws Exception{
        databaseService.cleanDatabase();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        List<DependenteDto> entities = ObjMotherDependente.criarLista5DtosUkDuplicado();
        entities.forEach(entity -> entity.setIdResponsavel(beneficiario.getId()));

        String json = JsonUtils.toJsonString(entities);

        mockMvc.perform(post(URL_TEMPLATE_LIST)
                        .header(AUTHORIZATION, getBearerToken(mockMvc))
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.cause").value(Matchers.endsWith(MessageUtils.get("ut.duplicate.uk.cause"))))
                .andExpect(jsonPath("$.message_error").value(Matchers.startsWith(MessageUtils.get("ut.duplicate.uk.message"))))
                .andExpect(jsonPath("$.path").value(URL_TEMPLATE_LIST));
    }

    @Test
    void wenGet_thenReturnsEntityList() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        DependenteDto dto = ObjMotherDependente.criarDto();
        dto.setIdResponsavel(beneficiario.getId());

        String json = JsonUtils.toJsonString(dto);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        mockMvc.perform(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].nome").value(dto.getNome()))
            .andExpect(jsonPath("$[0].telefone").value(dto.getTelefone()))
            .andExpect(jsonPath("$[0].email").value(dto.getEmail()))
            .andExpect(jsonPath("$[0].cpf").value(dto.getCpf()))
            .andExpect(jsonPath("$[0].data_nascimento").value(dto.getDataNascimento().toString()));
    }

    @Test
    void wenPut_thenReturnsUpdatedEntity() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        DependenteDto dto = ObjMotherDependente.criarDto();
        dto.setIdResponsavel(beneficiario.getId());

        String json = JsonUtils.toJsonString(dto);

        String storedEntityJson = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        DependenteDto storedEntity = JsonUtils.toObject(storedEntityJson, DependenteDto.class);

        storedEntity.setNome("IVAN MATOS DANTE");
        storedEntity.setEmail("new_email@gmail.com");

        mockMvc.perform(post(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(JsonUtils.toJsonString(storedEntity))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.nome").value(storedEntity.getNome()))
            .andExpect(jsonPath("$.email").value(storedEntity.getEmail()));
    }

    @Test
    void wenDelete_thenReturnsSuccessMessage() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        DependenteDto dto = ObjMotherDependente.criarDto();
        dto.setIdResponsavel(beneficiario.getId());

        String json = JsonUtils.toJsonString(dto);

        String storedEntityJson = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        DependenteDto storedEntity = JsonUtils.toObject(storedEntityJson, DependenteDto.class);

        mockMvc.perform(delete(URL_TEMPLATE + "/" + storedEntity.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
            .andExpect(status().isOk())
            .andExpect(content().string(MessageUtils.get("dependente.success.remove", storedEntity.getNome())));
    }
}
