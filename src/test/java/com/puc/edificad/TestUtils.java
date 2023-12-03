package com.puc.edificad;

import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.services.edsuser.dto.Login;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestUtils {
    private TestUtils() {
    }

    public static String getToken(MockMvc mockMvc) {
        return getToken(mockMvc, new Login("admin", "123"));
    }

    public static String getToken(MockMvc mockMvc, Login login) {
        String jsonLogin = JsonUtils.toJsonString(login);

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                            .content(jsonLogin)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String content = mvcResult.getResponse().getContentAsString();
            JSONObject jsonObject = new JSONObject(content);

            return jsonObject.getString("token");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String getBearerToken(MockMvc mockMvc){
        return "Bearer " + getToken(mockMvc);
    }

    public static String getBearerToken(MockMvc mockMvc, Login login){
        return "Bearer " + getToken(mockMvc, login);
    }


    public static String performsPost_thenTestStatusAndGetJson(MockMvc mockMvc, String urlTemplate, String json) throws Exception {
        return mockMvc.perform(post(urlTemplate)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(mockMvc))
                .content(json).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
    }

    public static void assertEmptyList(MockMvc mockMvc, RequestBuilder requestBuilder) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
