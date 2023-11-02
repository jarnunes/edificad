package com.puc.edificad.controller;

import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.commons.utils.MessageUtils;
import com.puc.edificad.model.Cesta;
import com.puc.edificad.object_mother.ObjMotherCesta;
import com.puc.edificad.services.CestaService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CestaControllerTest {

    private static final String URL_TEMPLATE = "/api/cesta";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CestaService cestaService;

    @Autowired
    private DistribuicaoCestaService distribuicaoCestaService;


    @Test
    public void wenPostCesta_thenReturnsCreatedCestaWithId() throws Exception {
        clearTablesUsedToTest();

        Cesta cesta = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(cesta);

        mockMvc.perform(post(URL_TEMPLATE).header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").value(cesta.getNome()))
            .andExpect(jsonPath("$.quantidade_estoque").value(cesta.getQuantidadeEstoque()))
            .andExpect(jsonPath("$.descricao").value(cesta.getDescricao()));
    }

    @Test
    public void wenGetCestas_thenReturnsCestaList() throws Exception {
        clearTablesUsedToTest();

        Cesta cesta = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(cesta);

        callsEndpointToCreate_thenTestStatusAndGetJson(json);

        mockMvc.perform(get(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].nome").value(cesta.getNome()))
            .andExpect(jsonPath("$[0].quantidade_estoque").value(cesta.getQuantidadeEstoque()))
            .andExpect(jsonPath("$[0].descricao").value(cesta.getDescricao()));
    }

    @Test
    public void wenPutCesta_thenReturnsUpdatedCesta() throws Exception {
        clearTablesUsedToTest();

        Cesta cesta = ObjMotherCesta.criar();
        String json = JsonUtils.toJsonString(cesta);

        String storedCestaJson = callsEndpointToCreate_thenTestStatusAndGetJson(json);
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
    public void wenDeleteCesta_thenReturnsSuccessMessage() throws Exception {
        clearTablesUsedToTest();

        String storedCestaJson = callsEndpointToCreate_thenTestStatusAndGetJson(ObjMotherCesta.criarJson());
        Cesta storedCesta = JsonUtils.toObject(storedCestaJson, Cesta.class);

        mockMvc.perform(delete(URL_TEMPLATE + "/" + storedCesta.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(content().string(MessageUtils.get("cesta.success.remove", storedCesta.getNome())));
    }

    private void clearTablesUsedToTest() {
        distribuicaoCestaService.findAll().forEach(distribuicaoCestaService::delete);
        cestaService.findAll().forEach(cestaService::delete);
    }

    private String callsEndpointToCreate_thenTestStatusAndGetJson(String json) throws Exception {
        return mockMvc.perform(post(URL_TEMPLATE)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
