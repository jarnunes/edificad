package com.puc.edificad.controller;

import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.commons.utils.MessageUtils;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.object_mother.ObjMotherVoluntario;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.DistribuicaoCestaService;
import com.puc.edificad.services.VoluntarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.puc.edificad.TestUtils.getBearerToken;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BeneficiarioControllerTest {
    private static final String URL_TEMPLATE = "/api/beneficiario";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeneficiarioService service;

    @Autowired
    private DistribuicaoCestaService distribuicaoCestaService;


    @Test
    public void wenPost_thenReturnsCreatedEntityWithId() throws Exception {
        clearTablesUsedToTest();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        mockMvc.perform(post(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json)
                .contentType(APPLICATION_JSON))
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

    @Test
    public void wenGet_thenReturnsEntityList() throws Exception {
        clearTablesUsedToTest();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        callsEndpointToCreate_thenTestStatusAndGetJson(json);

        mockMvc.perform(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
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

    @Test
    public void wenPut_thenReturnsUpdatedEntity() throws Exception {
        clearTablesUsedToTest();

        Beneficiario entity = ObjMotherBeneficiario.criar();
        String json = JsonUtils.toJsonString(entity);

        String storedEntityJson = callsEndpointToCreate_thenTestStatusAndGetJson(json);
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
    public void wenDelete_thenReturnsSuccessMessage() throws Exception {
        clearTablesUsedToTest();

        String storedEntityJson = callsEndpointToCreate_thenTestStatusAndGetJson(ObjMotherVoluntario.criarJson());
        Beneficiario storedEntity = JsonUtils.toObject(storedEntityJson, Beneficiario.class);

        mockMvc.perform(delete(URL_TEMPLATE + "/" + storedEntity.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(content().string(MessageUtils.get("beneficiario.success.remove", storedEntity.getNome())));
    }

    private void clearTablesUsedToTest() {
        distribuicaoCestaService.findAll().forEach(distribuicaoCestaService::delete);
        service.findAll().forEach(service::delete);
    }

    private String callsEndpointToCreate_thenTestStatusAndGetJson(String json) throws Exception {
        return mockMvc.perform(post(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
