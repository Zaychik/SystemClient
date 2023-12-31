package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
import com.zaychik.learning.system_user_rest.model.auth.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = BEFORE_CLASS)
public class IntegrationAuthenticationControllerTest extends AbstractIntegrationUserTest{
    @Test
    @DisplayName("Регистрация пользователя, проверка что можно авторизоваться им")
    void register_Success() throws Exception {
        RegisterRequest userNew = RegisterRequest.builder()
                .email("user4@gmail.com")
                .name("Alexander")
                .password("1234")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userNew)))
                .andDo(print())
                .andExpect(status().isOk());

        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user4@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        Assertions.assertFalse(response.getToken().equals(""));
    }
    @Test
    @DisplayName("Регистрация пользователя, не уникальная почта - ошибка")
    void register_ExistEmail_Error() throws Exception {
        RegisterRequest userNew = RegisterRequest.builder()
                .email("user2@gmail.com")
                .name("Alexander")
                .password("1234")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userNew)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("users_contraint_email")));

    }


    @Test
    @DisplayName("Регистрация пользователя, не указан пароль - ошибка")
    void register_NoSetPasswordEmail_Error() throws Exception {
        RegisterRequest userNew = RegisterRequest.builder()
                .email("user3@gmail.com")
                .name("Alexander")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userNew)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Enter a password")));

    }

    @Test
    @DisplayName("Авторизация, успешная")
    void authenticate_Success() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        Assertions.assertFalse(response.getToken().equals(""));
    }

    @Test
    @DisplayName("Авторизация пользователя, не правильный пароль - ошибка")
    void authenticate_NoCorrectPassword_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("12345")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/authentication")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Авторизация пользователя, нет такого пользователя - ошибка")
    void authenticate_NoExistUser_Error() throws Exception {
        AuthenticationRequest user10 = AuthenticationRequest.builder()
                .email("user10@gmail.com")
                .password("1234")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/authentication")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user10)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }





}
