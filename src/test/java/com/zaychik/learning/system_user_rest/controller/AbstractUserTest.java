package com.zaychik.learning.system_user_rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
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
public abstract class AbstractUserTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    AuthenticationResponce performAuthentication(AuthenticationRequest user) throws Exception {
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
}
