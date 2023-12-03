package com.puc.edificad.controller;

import com.puc.edificad.TestUtils;
import com.puc.edificad.commons.object_mother.ObjMotherConfiguracao;
import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.model.Configuracao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static com.puc.edificad.TestUtils.getBearerToken;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ConfiguracaoControllerTest {
    private static final String URL_TEMPLATE = "/api/config";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void wenPost_thenReturnsCreatedEntity() throws Exception {
        Configuracao entity = ObjMotherConfiguracao.criar();
        String json = JsonUtils.toJsonString(entity);

        commonAssertEntity(post(URL_TEMPLATE)
            .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
            .content(json)
            .contentType(APPLICATION_JSON), entity);
    }

    @Test
    void wenGet_thenReturnsEntity() throws Exception {
        Configuracao entity = ObjMotherConfiguracao.criar();
        String json = JsonUtils.toJsonString(entity);

        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        Configuracao configuracaoResponse = JsonUtils.toObject(jsonResponse, Configuracao.class);

        commonAssertEntity(get(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)),
            configuracaoResponse);
    }

    @Test
    void wenPut_thenReturnsUpdatedEntity() throws Exception {
        Configuracao entity = ObjMotherConfiguracao.criar();
        String json = JsonUtils.toJsonString(entity);

        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        Configuracao configuracaoResponse = JsonUtils.toObject(jsonResponse, Configuracao.class);

        configuracaoResponse.setTokenExpiresAt(15000);

        commonAssertEntity(put(URL_TEMPLATE)
            .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
            .content(JsonUtils.toJsonString(configuracaoResponse)).contentType(APPLICATION_JSON), configuracaoResponse);
    }

    private void commonAssertEntity(RequestBuilder requestBuilder, Configuracao entity) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.token_secret_key").value(entity.getTokenSecretKey()))
                .andExpect(jsonPath("$.token_expires_at").value(entity.getTokenExpiresAt()));
    }

}
