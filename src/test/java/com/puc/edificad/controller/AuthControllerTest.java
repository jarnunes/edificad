package com.puc.edificad.controller;

import com.jnunes.spgauth.model.User;
import com.jnunes.spgauth.model.dto.Login;
import com.puc.edificad.TestUtils;
import com.puc.edificad.commons.object_mother.ObjMotherUser;
import com.jnunes.spgcore.commons.utils.JsonUtils;
import com.jnunes.spgcore.commons.utils.MessageUtils;
import com.puc.edificad.services.DatabaseService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    private static final String URL_TEMPLATE = "/api/auth";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DatabaseService databaseService;

    @BeforeEach
    public void cleanDatabase(){
        databaseService.cleanUserTables();
        databaseService.criarUsuarioRequisicoes();
    }

    @Test
    void wenLoginPost_thenReturnsAccessToken() throws Exception {
        String json = JsonUtils.toJsonString(new Login("admin", "123"));

        mockMvc.perform(post(URL_TEMPLATE + "/login")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void wenGet_thenReturnsEntityList() throws Exception {
        User entity = ObjMotherUser.criar();
        String json = JsonUtils.toJsonString(entity);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        commonAssertEntityList(get(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)));
    }

    @Test
    void wenPost_thenReturnsCreatedEntity() throws Exception {
        User entity = ObjMotherUser.criar();
        String json = JsonUtils.toJsonString(entity);

        commonAssertEntity(post(URL_TEMPLATE)
            .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
            .content(json).contentType(APPLICATION_JSON), entity);
    }

    @Test
    void wenGetByEmail_thenReturnsEntity() throws Exception{
        User entity = ObjMotherUser.criar();
        String json = JsonUtils.toJsonString(entity);

        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        User userResponse = JsonUtils.toObject(jsonResponse, User.class);

        commonAssertEntity(get(URL_TEMPLATE + "/{email}", userResponse.getEmail())
            .header(AUTHORIZATION, getBearerToken(mockMvc)), entity);
    }


    @Test
    void wenResetPassword_thenReturnsUpdatedUsernameAndNewPassword() throws Exception {
        User entity = ObjMotherUser.criar();
        String json = JsonUtils.toJsonString(entity);

        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);
        User userResponse = JsonUtils.toObject(jsonResponse, User.class);

        Login login = new Login(userResponse.getUsername(), "novasenha");

        mockMvc.perform(post(URL_TEMPLATE+ "/reset-password")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(JsonUtils.toJsonString(login))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(MessageUtils.get("eds.success.updated.password")));
    }

    @Test
    void wenDelete_thenReturnsSuccessMessage() throws Exception {
        String jsonResponse = TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE,
            ObjMotherUser.criarJson());

        User userResponse = JsonUtils.toObject(jsonResponse, User.class);

        mockMvc.perform(delete(URL_TEMPLATE + "/{id}", userResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
            .andExpect(status().isOk())
            .andExpect(content().string(MessageUtils.get("eds.success.remove.user", userResponse.getUsername())));
    }

    private void commonAssertEntityList(RequestBuilder requestBuilder) throws Exception {
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(Matchers.greaterThan(0))));
    }

    private void commonAssertEntity(RequestBuilder requestBuilder, User entity) throws Exception {
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.full_name").value(entity.getFullName()))
            .andExpect(jsonPath("$.username").value(entity.getUsername()))
            .andExpect(jsonPath("$.email").value(entity.getEmail()))
            .andExpect(jsonPath("$.password").exists())
            .andExpect(jsonPath("$.enabled").value("true"))
            .andExpect(jsonPath("$.locked").value("false"))
            .andExpect(jsonPath("$.user_roles", hasSize(Matchers.greaterThan(0))));
    }

}
