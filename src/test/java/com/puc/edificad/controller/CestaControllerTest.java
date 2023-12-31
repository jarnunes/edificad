package com.puc.edificad.controller;

import com.puc.edificad.TestUtils;
import com.puc.edificad.commons.object_mother.ObjMotherCesta;
import com.puc.edificad.commons.utils.JsonUtils;
import com.jnunes.spgcore.commons.utils.MessageUtils;
import com.puc.edificad.model.Cesta;
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

@ExtendWith(SpringExtension.class)@SpringBootTest
@AutoConfigureMockMvc
class CestaControllerTest {

    private static final String URL_TEMPLATE = "/api/cesta";
    private static final String URL_TEMPLATE_LIST = URL_TEMPLATE+ "/create-from-list";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DatabaseService databaseService;

    @Test
    void wenPost_thenReturnsCreatedEntityWithId() throws Exception {
        databaseService.cleanDatabase();

        Cesta cesta = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(cesta);

        commonAssertEntity(post(URL_TEMPLATE)
            .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
            .content(json)
            .contentType(APPLICATION_JSON), cesta);
    }

    @Test
    void wenPostList_thenReturnsCreatedEntitiesList() throws Exception{
        databaseService.cleanDatabase();

        List<Cesta> entities = ObjMotherCesta.criarLista5Entidades();
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

        List<Cesta> entities = ObjMotherCesta.criarLista5EntidadesUniqueKeyDuplicada();
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

        Cesta cesta = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(cesta);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        commonAssertEntityList(get(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)), cesta);
    }

    @Test
    void wenGetById_thenReturnsEntity() throws Exception{
        databaseService.cleanDatabase();

        Cesta entity = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(entity);

        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        commonAssertEntity(get(URL_TEMPLATE + "/{id}", JsonUtils.getAttr(jsonResponse, "id"))
                .header(AUTHORIZATION, getBearerToken(mockMvc)), entity);
    }

    @Test
    void wenGetByCorrectQueryParam_thenReturnsEntityList() throws Exception {
        databaseService.cleanDatabase();

        Cesta entity = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(entity);

        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        Cesta cestaResponse = JsonUtils.toObject(jsonResponse, Cesta.class);

        commonAssertEntityList(get(URL_TEMPLATE).header(AUTHORIZATION, getBearerToken(mockMvc))
                .param("id", String.valueOf(cestaResponse.getId())), entity);

        commonAssertEntityList(get(URL_TEMPLATE).header(AUTHORIZATION, getBearerToken(mockMvc))
                .param("nome", cestaResponse.getNome()), entity);
    }

    @Test
    void wenGetByIncorrectQueryParam_thenReturnsEmptyList() throws Exception {
        databaseService.cleanDatabase();

        Cesta entity = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(entity);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        TestUtils.assertEmptyList(mockMvc, get(URL_TEMPLATE)
                .header(AUTHORIZATION, getBearerToken(mockMvc))
                .param("id", "0"));

        TestUtils.assertEmptyList(mockMvc, get(URL_TEMPLATE)
                .header(AUTHORIZATION, getBearerToken(mockMvc))
                .param("nome", "NOME_ALEATORIO"));
    }

    @Test
    void wenPut_thenReturnsUpdatedEntity() throws Exception {
        databaseService.cleanDatabase();

        Cesta cesta = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(cesta);

        String storedCestaJson = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        Cesta storedCesta = JsonUtils.toObject(storedCestaJson, Cesta.class);

        storedCesta.setQuantidadeEstoque(3400);
        storedCesta.setDescricao("Descricao Atualizada");

        mockMvc.perform(post(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(JsonUtils.toJsonString(storedCesta))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.quantidade_estoque").value(storedCesta.getQuantidadeEstoque()))
            .andExpect(jsonPath("$.descricao").value(storedCesta.getDescricao()));
    }

    @Test
    void wenDelete_thenReturnsSuccessMessage() throws Exception {
        databaseService.cleanDatabase();

        String storedCestaJson = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE,
            ObjMotherCesta.criarJson());
        Cesta storedCesta = JsonUtils.toObject(storedCestaJson, Cesta.class);

        mockMvc.perform(delete(URL_TEMPLATE + "/" + storedCesta.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(content().string(MessageUtils.get("cesta.success.remove", storedCesta.getNome())));
    }


    private void commonAssertEntity(RequestBuilder requestBuilder, Cesta entity) throws Exception {
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").value(entity.getNome()))
            .andExpect(jsonPath("$.quantidade_estoque").value(entity.getQuantidadeEstoque()))
            .andExpect(jsonPath("$.descricao").value(entity.getDescricao()));
    }

    private void commonAssertEntityList(RequestBuilder requestBuilder, Cesta entity) throws Exception {
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].nome").value(entity.getNome()))
            .andExpect(jsonPath("$[0].quantidade_estoque").value(entity.getQuantidadeEstoque()))
            .andExpect(jsonPath("$[0].descricao").value(entity.getDescricao()));
    }

}
