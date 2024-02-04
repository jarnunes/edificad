package com.puc.edificad.controller;

import com.puc.edificad.TestUtils;
import com.puc.edificad.commons.object_mother.ObjMotherBeneficiario;
import com.puc.edificad.commons.object_mother.ObjMotherCesta;
import com.puc.edificad.commons.object_mother.ObjMotherDistribuicaoCesta;
import com.puc.edificad.commons.object_mother.ObjMotherVoluntario;
import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import com.jnunes.spgcore.commons.utils.JsonUtils;
import com.puc.edificad.model.Beneficiario;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.model.Voluntario;
import com.puc.edificad.model.dto.DistribuicaoCestaDto;
import com.puc.edificad.services.BeneficiarioService;
import com.puc.edificad.services.CestaService;
import com.puc.edificad.services.DatabaseService;
import com.puc.edificad.services.VoluntarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;

import static com.puc.edificad.TestUtils.getBearerToken;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DistribuicaoCestaControllerTest {

    private static final String URL_TEMPLATE = "/api/distribuicao-cesta";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CestaService cestaService;

    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private BeneficiarioService beneficiarioService;
    @Autowired
    private DatabaseService databaseService;
    @Test
    void wenPost_thenReturnsCreatedEntityWithId() throws Exception {
        databaseService.cleanDatabase();

        Voluntario voluntario = ObjMotherVoluntario.criar();
        voluntarioService.save(voluntario);

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        Cesta cesta = ObjMotherCesta.criar();
        cestaService.save(cesta);

        DistribuicaoCestaDto distribuicaoCesta = ObjMotherDistribuicaoCesta.criar();
        distribuicaoCesta.getVoluntario().setId(voluntario.getId());
        distribuicaoCesta.getBeneficiario().setId(beneficiario.getId());
        distribuicaoCesta.getCesta().setId(cesta.getId());

        String json = JsonUtils.toJsonString(distribuicaoCesta);

        mockMvc.perform(post(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.data_hora").value(DateTimeUtils.formatter(distribuicaoCesta.getDataHora())))
            .andExpect(jsonPath("$.beneficiario.id").value(distribuicaoCesta.getBeneficiario().getId()))
            .andExpect(jsonPath("$.beneficiario.nome").value(beneficiario.getNome()))
            .andExpect(jsonPath("$.beneficiario.email").value(beneficiario.getEmail()))
            .andExpect(jsonPath("$.beneficiario.cpf").value(beneficiario.getCpf()))
            .andExpect(jsonPath("$.beneficiario.telefone").value(beneficiario.getTelefone()))
            .andExpect(jsonPath("$.beneficiario.data_nascimento").value(beneficiario.getDataNascimento().toString()))
            .andExpect(jsonPath("$.voluntario.id").value(distribuicaoCesta.getVoluntario().getId()))
            .andExpect(jsonPath("$.voluntario.nome").value(voluntario.getNome()))
            .andExpect(jsonPath("$.voluntario.email").value(voluntario.getEmail()))
            .andExpect(jsonPath("$.voluntario.cpf").value(voluntario.getCpf()))
            .andExpect(jsonPath("$.voluntario.telefone").value(voluntario.getTelefone()))
            .andExpect(jsonPath("$.voluntario.data_nascimento").value(voluntario.getDataNascimento().toString()))
            .andExpect(jsonPath("$.voluntario.qtde_projetos_participado").value(voluntario.getNumeroProjetosParticipados()))
            .andExpect(jsonPath("$.cesta.id").value(distribuicaoCesta.getCesta().getId()))
            .andExpect(jsonPath("$.cesta.nome").value(cesta.getNome()));
    }

    @Test
    void wenGet_thenReturnsEntityList() throws Exception {
        databaseService.cleanDatabase();

        Voluntario voluntario = ObjMotherVoluntario.criar();
        voluntarioService.save(voluntario);

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        Cesta cesta = ObjMotherCesta.criar();
        cestaService.save(cesta);

        DistribuicaoCestaDto distribuicaoCesta = ObjMotherDistribuicaoCesta.criar();
        distribuicaoCesta.getVoluntario().setId(voluntario.getId());
        distribuicaoCesta.getBeneficiario().setId(beneficiario.getId());
        distribuicaoCesta.getCesta().setId(cesta.getId());
        String json = JsonUtils.toJsonString(distribuicaoCesta);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        commonAssertEntityList(get(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)),
                distribuicaoCesta, beneficiario, voluntario, cesta);
    }

    @Test
    void wenGetByCorrectQueryParam_thenReturnsEntityList() throws Exception {
        databaseService.cleanDatabase();

        Voluntario voluntario = ObjMotherVoluntario.criar();
        voluntarioService.save(voluntario);

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        Cesta cesta = ObjMotherCesta.criar();
        cestaService.save(cesta);

        DistribuicaoCestaDto distribuicaoCesta = ObjMotherDistribuicaoCesta.criar();
        distribuicaoCesta.getVoluntario().setId(voluntario.getId());
        distribuicaoCesta.getBeneficiario().setId(beneficiario.getId());
        distribuicaoCesta.getCesta().setId(cesta.getId());
        String json = JsonUtils.toJsonString(distribuicaoCesta);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        commonAssertEntityList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("cesta", cesta.getNome()),
                distribuicaoCesta, beneficiario, voluntario, cesta);

        commonAssertEntityList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("cpfBeneficiario", beneficiario.getCpf()),
                distribuicaoCesta, beneficiario, voluntario, cesta);

        commonAssertEntityList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("cpfVoluntario", voluntario.getCpf()),
                distribuicaoCesta, beneficiario, voluntario, cesta);

        commonAssertEntityList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("data", DateTimeUtils.toDate(distribuicaoCesta.getDataHora())),
                distribuicaoCesta, beneficiario, voluntario, cesta);
    }


    @Test
    void wenGetByIncorrectQueryParam_thenReturnsEmptyList() throws Exception {
        databaseService.cleanDatabase();

        Voluntario voluntario = ObjMotherVoluntario.criar();
        voluntarioService.save(voluntario);

        Beneficiario beneficiario = ObjMotherBeneficiario.criar();
        beneficiarioService.save(beneficiario);

        Cesta cesta = ObjMotherCesta.criar();
        cestaService.save(cesta);

        DistribuicaoCestaDto distribuicaoCesta = ObjMotherDistribuicaoCesta.criar();
        distribuicaoCesta.getVoluntario().setId(voluntario.getId());
        distribuicaoCesta.getBeneficiario().setId(beneficiario.getId());
        distribuicaoCesta.getCesta().setId(cesta.getId());
        String json = JsonUtils.toJsonString(distribuicaoCesta);

        TestUtils.performsPost_thenTestStatusAndGetJson(mockMvc, URL_TEMPLATE, json);

        commonAssertEmptyList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("cesta", "NOME_ALEATORIO"));

        commonAssertEmptyList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("cpfBeneficiario", "000.000.000-00"));

        commonAssertEmptyList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("cpfVoluntario", "000.000.000-00"));

        commonAssertEmptyList(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .param("data", LocalDate.now().plusDays(2).toString()));
    }

    private void commonAssertEntityList(RequestBuilder requestBuilder, DistribuicaoCestaDto distribuicaoCesta,
        Beneficiario beneficiario, Voluntario voluntario, Cesta cesta) throws Exception {
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].data_hora").value(DateTimeUtils.formatter(distribuicaoCesta.getDataHora())))
            .andExpect(jsonPath("$[0].beneficiario.id").value(distribuicaoCesta.getBeneficiario().getId()))
            .andExpect(jsonPath("$[0].beneficiario.nome").value(beneficiario.getNome()))
            .andExpect(jsonPath("$[0].beneficiario.email").value(beneficiario.getEmail()))
            .andExpect(jsonPath("$[0].beneficiario.cpf").value(beneficiario.getCpf()))
            .andExpect(jsonPath("$[0].beneficiario.telefone").value(beneficiario.getTelefone()))
            .andExpect(jsonPath("$[0].beneficiario.data_nascimento").value(beneficiario.getDataNascimento().toString()))
            .andExpect(jsonPath("$[0].voluntario.id").value(distribuicaoCesta.getVoluntario().getId()))
            .andExpect(jsonPath("$[0].voluntario.nome").value(voluntario.getNome()))
            .andExpect(jsonPath("$[0].voluntario.email").value(voluntario.getEmail()))
            .andExpect(jsonPath("$[0].voluntario.cpf").value(voluntario.getCpf()))
            .andExpect(jsonPath("$[0].voluntario.telefone").value(voluntario.getTelefone()))
            .andExpect(jsonPath("$[0].voluntario.data_nascimento").value(voluntario.getDataNascimento().toString()))
            .andExpect(jsonPath("$[0].voluntario.qtde_projetos_participado").value(voluntario.getNumeroProjetosParticipados()))
            .andExpect(jsonPath("$[0].cesta.id").value(distribuicaoCesta.getCesta().getId()))
            .andExpect(jsonPath("$[0].cesta.nome").value(cesta.getNome()));
    }


    private void commonAssertEmptyList(RequestBuilder requestBuilder) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
