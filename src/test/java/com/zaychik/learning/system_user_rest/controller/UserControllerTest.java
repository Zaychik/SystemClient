package com.zaychik.learning.system_user_rest.controller;


import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends AbstractUserTest {
    @Autowired
    private MockMvc mvc;

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
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получив токен не админа, получить список всех пользователей через get ./users ")
    void read_GetAllUsersWithUserToken_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Получив токен админа, получить пользователя через get ./users ")
    void read_GetOneUserWithAdminToken_Success() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("admin@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Получив токен не админа, получить пользователя через get ./users ")
    void read_GetOneUserWithUserToken_Success() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Получив токен не админа, получить пользователя через get ./users ")
    void read_GetNoExistUserWithUserToken_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/3")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}