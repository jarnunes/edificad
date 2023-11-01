package com.puc.edificad.controller;

import com.puc.edificad.TestUtils;
import com.puc.edificad.object_mother.ObjMotherCesta;
import com.puc.edificad.services.CestaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CestaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CestaService cestaService;


    @Test
    public void create() throws Exception {
        deleteAll();

        String json = ObjMotherCesta.criarJson();
        mockMvc.perform(post("/api/cesta")
                    .header(HttpHeaders.AUTHORIZATION, TestUtils.getBearerToken(mockMvc))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    @Test
    public void wenGetCestas_thenReturnCestaList() throws Exception {
        mockMvc.perform(get("/api/cesta")
                .header(HttpHeaders.AUTHORIZATION, TestUtils.getBearerToken(mockMvc)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name", is("John")))
            .andExpect(jsonPath("$[1].name", is("Jane")));
    }

    private void deleteAll(){
        cestaService.findAll().forEach(cestaService::delete);
    }
}
