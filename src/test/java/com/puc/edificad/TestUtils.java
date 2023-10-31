package com.puc.edificad;

import com.puc.edificad.commons.utils.JsonUtils;
import com.puc.edificad.services.edsuser.dto.Login;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TestUtils {
    private TestUtils() {
    }

    public static String getToken(MockMvc mockMvc) {
        String jsonLogin = JsonUtils.toJsonString(new Login("admin", "123"));

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
}
