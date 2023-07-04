package com.zaychik.learning.system_user_rest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private AuthenticationResponce performAuthentication(AuthenticationRequest user) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/authentication")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Gson gson = new Gson();
        AuthenticationResponce response = gson.fromJson(result.getResponse().getContentAsString(), AuthenticationResponce.class);
        return  response;
    }
    @Test
    @DisplayName("Получив токен админа, получить список всех пользователей через get ./users ")
    void read_GetAllUsersWithAdminToken_Success() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("admin@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}