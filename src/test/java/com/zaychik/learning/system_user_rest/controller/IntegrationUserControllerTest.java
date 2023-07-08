package com.zaychik.learning.system_user_rest.controller;

import com.google.gson.Gson;
import com.zaychik.learning.system_user_rest.model.Role;
import com.zaychik.learning.system_user_rest.model.UserDto;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IntegrationUserControllerTest extends AbstractIntegrationUserTest {

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
                        .get("/users/5")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Получив токен админа, удачно изменить 1-ого пользователя")
    void update_DoWithAdminTokenExistUser_Success() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("admin@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        UserDto userNewDtoUser = UserDto.builder()
                .id(1)
                .email("user@gmail.com")
                .phone("8111222333")
                .name("Alexander")
                .role(Role.USER)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .put("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userNewDtoUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Gson gson = new Gson();
        UserDto userDtoUser = gson.fromJson(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals(userDtoUser, userNewDtoUser);
    }

    @Test
    @DisplayName("Получив токен не админа, не удачно изменить 1-ого пользователя")
    void update_DoWithNoAdminTokenExistUser_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        UserDto userNewDtoUser = UserDto.builder()
                .id(1)
                .email("user@gmail.com")
                .phone("8111222333")
                .name("Alexander")
                .role(Role.USER)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .put("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userNewDtoUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("Получив токен админа, не удачно изменить у 1-ого пользователя на существующий email ")
    void update_DoWithAdminTokenExistUserEmailYetExist_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("admin@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        UserDto userNewDtoUser = UserDto.builder()
                .id(1)
                .email("admin@gmail.com")
                .phone("8111222333")
                .name("Alexander")
                .role(Role.USER)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .put("/users/1")
                        .header("authorization", "Bearer " + response.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userNewDtoUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("users_contraint_email")));
    }
    @Test
    @DisplayName("Получив токен админа, не удачно изменить не существующего пользователя")
    void update_DoWithAdminNoExistUser_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("admin@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        UserDto userNewDtoUser = UserDto.builder()
                .id(3)
                .email("user@gmail.com")
                .phone("8111222333")
                .name("Alexander")
                .role(Role.USER)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .put("/users/5")
                        .header("authorization", "Bearer " + response.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userNewDtoUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Получив токен админа, не удачно удалить не существующего пользователя")
    void delete_DoWithAdminNoExistUser_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("admin@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/users/5")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Получив токен не админа, не удачно удалить 3-ого пользователя")
    void delete_DoWithNoAdminDeleteExistUser_Error() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("user@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/users/5")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Получив токен админа, удачно удалить 3-ого пользователя из")
    void delete_DoWithAdminDeleteExistUser_Success() throws Exception {
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("admin@gmail.com")
                .password("1234")
                .build();
        AuthenticationResponce response = performAuthentication(user);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/users/3")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/3")
                        .header("authorization", "Bearer " + response.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}