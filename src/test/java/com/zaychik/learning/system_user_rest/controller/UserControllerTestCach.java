package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestCach extends AbstractUserTest {
    @Autowired
    private MockMvc mvc;
    @SpyBean
    UserRepository userRepository;

    @Test
    @DisplayName("Получив токен админа, получить пользователя - сходить в БД один раз, а запросить два раза")
    void read_GetOneUserWithAdminTokenTwice_OnceBDSuccess() throws Exception {
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
                .andExpect(status().isOk())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
    }
}
