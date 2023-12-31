package com.puc.edificad.controller;

import com.puc.edificad.TestUtils;
import com.puc.edificad.commons.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.commons.object_mother.ObjMotherVoluntario;
import com.puc.edificad.commons.utils.JsonUtils;
import com.jnunes.core.commons.utils.MessageUtils;
import com.puc.edificad.model.Beneficiario;
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
import org.springframework.test.web.servlet.RequestBuilder;

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
class BeneficiarioControllerTest {
    private static final String URL_TEMPLATE = "/api/beneficiario";
    private static final String URL_TEMPLATE_LIST = URL_TEMPLATE+ "/create-from-list";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DatabaseService databaseService;

    @Test
    void wenPost_thenReturnsCreatedEntityWithId() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        commonAssertEntity(post(URL_TEMPLATE)
            .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
            .content(json)
            .contentType(APPLICATION_JSON), entity);
    }

    @Test
    void wenPostList_thenReturnsCreatedEntitiesList() throws Exception{
        databaseService.cleanDatabase();

        List<Beneficiario> entities = ObjMotherBeneficiario.criarLista5Entidades();
        String json = JsonUtils.toJsonString(entities);

        mockMvc.perform(post(URL_TEMPLATE + "/create-from-list")
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

        List<Beneficiario> entities = ObjMotherBeneficiario.criarLista5EntidadesUniqueKeyDuplicada();
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

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        commonAssertEntityList(get(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)), entity);
    }

    @Test
    void wenGetById_thenReturnsEntity() throws Exception{
        databaseService.cleanDatabase();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        commonAssertEntity(get(URL_TEMPLATE + "/{id}", JsonUtils.getAttr(jsonResponse, "id"))
            .header(AUTHORIZATION, getBearerToken(mockMvc)), entity);
    }

    @Test
    void wenGetByCorrectQueryParam_thenReturnsEntityList() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        String storedEntity = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        commonAssertEntityList(get(URL_TEMPLATE).header(AUTHORIZATION, getBearerToken(mockMvc))
            .param("id", JsonUtils.getAttr(storedEntity, "id")), entity);

        commonAssertEntityList(get(URL_TEMPLATE).header(AUTHORIZATION, getBearerToken(mockMvc))
            .param("nome", entity.getNome()), entity);

        commonAssertEntityList(get(URL_TEMPLATE).header(AUTHORIZATION, getBearerToken(mockMvc))
            .param("cpf", entity.getCpf()), entity);
    }

    @Test
    void wenGetByIncorrectQueryParam_thenReturnsEmptyList() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        TestUtils.assertEmptyList(mockMvc, get(URL_TEMPLATE)
            .header(AUTHORIZATION, getBearerToken(mockMvc))
            .param("id", "0"));

        TestUtils.assertEmptyList(mockMvc, get(URL_TEMPLATE)
            .header(AUTHORIZATION, getBearerToken(mockMvc))
            .param("nome", "NOME_ALEATORIO"));

        TestUtils.assertEmptyList(mockMvc, get(URL_TEMPLATE)
            .header(AUTHORIZATION, getBearerToken(mockMvc))
            .param("cpf", "000.000.000-00"));
    }

    @Test
    void wenPut_thenReturnsUpdatedEntity() throws Exception {
        databaseService.cleanDatabase();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        String storedEntityJson = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        Beneficiario storedEntity = JsonUtils.toObject(storedEntityJson, Beneficiario.class);

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

        String storedEntityJson = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE,
            ObjMotherVoluntario.criarJson());

        Beneficiario storedEntity = JsonUtils.toObject(storedEntityJson, Beneficiario.class);

        mockMvc.perform(delete(URL_TEMPLATE + "/" + storedEntity.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(content().string(MessageUtils.get("beneficiario.success.remove", storedEntity.getNome())));
    }

    private void commonAssertEntityList(RequestBuilder requestBuilder, Beneficiario entity) throws Exception {
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].nome").value(entity.getNome()))
            .andExpect(jsonPath("$[0].telefone").value(entity.getTelefone()))
            .andExpect(jsonPath("$[0].email").value(entity.getEmail()))
            .andExpect(jsonPath("$[0].cpf").value(entity.getCpf()))
            .andExpect(jsonPath("$[0].data_nascimento").value(entity.getDataNascimento().toString()))
            .andExpect(jsonPath("$[0].endereco").exists())
            .andExpect(jsonPath("$[0].endereco.id").exists())
            .andExpect(jsonPath("$[0].endereco.logradouro").value(entity.getEndereco().getLogradouro()))
            .andExpect(jsonPath("$[0].endereco.numero").value(entity.getEndereco().getNumero()))
            .andExpect(jsonPath("$[0].endereco.cep").value(entity.getEndereco().getCep()))
            .andExpect(jsonPath("$[0].endereco.bairro").value(entity.getEndereco().getBairro()))
            .andExpect(jsonPath("$[0].endereco.cidade").value(entity.getEndereco().getCidade()))
            .andExpect(jsonPath("$[0].endereco.estado").value(String.valueOf(entity.getEndereco().getEstado())));
    }

    private void commonAssertEntity(RequestBuilder requestBuilder, Beneficiario entity) throws Exception {
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").value(entity.getNome()))
            .andExpect(jsonPath("$.telefone").value(entity.getTelefone()))
            .andExpect(jsonPath("$.email").value(entity.getEmail()))
            .andExpect(jsonPath("$.cpf").value(entity.getCpf()))
            .andExpect(jsonPath("$.data_nascimento").value(entity.getDataNascimento().toString()))
            .andExpect(jsonPath("$.endereco").exists())
            .andExpect(jsonPath("$.endereco.id").exists())
            .andExpect(jsonPath("$.endereco.logradouro").value(entity.getEndereco().getLogradouro()))
            .andExpect(jsonPath("$.endereco.numero").value(entity.getEndereco().getNumero()))
            .andExpect(jsonPath("$.endereco.cep").value(entity.getEndereco().getCep()))
            .andExpect(jsonPath("$.endereco.bairro").value(entity.getEndereco().getBairro()))
            .andExpect(jsonPath("$.endereco.cidade").value(entity.getEndereco().getCidade()))
            .andExpect(jsonPath("$.endereco.estado").value(String.valueOf(entity.getEndereco().getEstado())));
    }

}
