package com.puc.edificad.controller;

import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.commons.utils.MessageUtils;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Dependente;
import com.puc.edificad.model.dto.DependenteDto;
import com.puc.edificad.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.object_mother.ObjMotherDependente;
import com.puc.edificad.object_mother.ObjMotherVoluntario;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.DependenteService;
import com.puc.edificad.services.DistribuicaoCestaService;
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
public class DependenteControllerTest {

    private static final String URL_TEMPLATE = "/api/dependente";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Autowired
    private DependenteService dependenteService;
    @Autowired
    private DistribuicaoCestaService distribuicaoCestaService;


    @Test
    public void wenPost_thenReturnsCreatedEntityWithId() throws Exception {
        clearTablesUsedToTest();

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
    public void wenGet_thenReturnsEntityList() throws Exception {
        clearTablesUsedToTest();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        DependenteDto dto = ObjMotherDependente.criarDto();
        dto.setIdResponsavel(beneficiario.getId());

        String json = JsonUtils.toJsonString(dto);

        callsEndpointToCreate_thenTestStatusAndGetJson(json);

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
    public void wenPut_thenReturnsUpdatedEntity() throws Exception {
        clearTablesUsedToTest();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        DependenteDto dto = ObjMotherDependente.criarDto();
        dto.setIdResponsavel(beneficiario.getId());

        String json = JsonUtils.toJsonString(dto);

        String storedEntityJson = callsEndpointToCreate_thenTestStatusAndGetJson(json);
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
    public void wenDelete_thenReturnsSuccessMessage() throws Exception {
        clearTablesUsedToTest();

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        DependenteDto dto = ObjMotherDependente.criarDto();
        dto.setIdResponsavel(beneficiario.getId());

        String json = JsonUtils.toJsonString(dto);

        String storedEntityJson = callsEndpointToCreate_thenTestStatusAndGetJson(json);
        DependenteDto storedEntity = JsonUtils.toObject(storedEntityJson, DependenteDto.class);

        mockMvc.perform(delete(URL_TEMPLATE + "/" + storedEntity.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(content().string(MessageUtils.get("dependente.success.remove", storedEntity.getNome())));
    }

    private void clearTablesUsedToTest() {
        distribuicaoCestaService.findAll().forEach(distribuicaoCestaService::delete);
        dependenteService.findAll().forEach(dependenteService::delete);
        beneficiarioService.findAll().forEach(beneficiarioService::delete);
    }

    private String callsEndpointToCreate_thenTestStatusAndGetJson(String json) throws Exception {
        return mockMvc.perform(post(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
