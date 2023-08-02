package com.zaychik.learning.system_user_rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = BEFORE_CLASS)
public class IntegrationUserLogControllerTest extends AbstractIntegrationUserTest{
    private static final String EMAIL = "admin@gmail.com";

    @Test
    @DisplayName("Получив токен админа, получить список всех пользователей ")
    void readLog_GetAllLogWithAdminToken_Success() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email(EMAIL)
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        mvc.perform(MockMvcRequestBuilders
                        .get("/users/2")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/users/log?email=" + EMAIL)
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Gson gson = new Gson();

        List<LogElement> logElementList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<LogElement>>(){});
        assertTrue(logElementList.size() >= 2 );
    }

    @Test
    @DisplayName("Получив токен не админа, получить список всех пользователей ")
    void readLog_GetAllLogWithNoAdminToken_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        UserDto userDtoUser = UserDto.builder()
                .email("admin@gmail.com")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .post("/users/log")
                        .header("authorization", "Bearer " + response.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDtoUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
