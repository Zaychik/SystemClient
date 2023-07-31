package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.controller.exception.NotFoundException;
import com.zaychik.learning.system_user_rest.model.Role;
import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final int ID = 1;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService service;
    @Test
    void get_whenExistUser_thereIsNoException() {
        final User user = User.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .password("qwert")
                .role(Role.USER)
                .build();

        final UserDto userDto = UserDto.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        UserDto actualuser = service.get(ID);
        Mockito.verify(userRepository, Mockito.times(1)).findById(ID);
        Assertions.assertEquals(userDto, actualuser);
    }

    @Test
    void get_whenNoExistUser_thereIsException() {
        final User user = null;
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            service.get(ID);
        });
        Assertions.assertEquals("User not found", thrown.getMessage());
    }

    @Test
    void getAll() {
        List<UserDto> expectedUserList = new LinkedList<UserDto>();
        UserDto userDtoUser = UserDto.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        expectedUserList.add(userDtoUser);
        UserDto userDtoAdmin = UserDto.builder()
                .id(2)
                .email("alex@gmail.com")
                .phone("811777888123")
                .name("Alex2")
                .role(Role.ADMIN)
                .build();
        expectedUserList.add(userDtoAdmin);

        List<User> userList = new LinkedList<User>();
        User userRoleUser = User.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .password("qwert")
                .role(Role.USER)
                .build();
        userList.add(userRoleUser);
        User userRoleAdmin = User.builder()
                .id(2)
                .email("alex@gmail.com")
                .phone("811777888123")
                .name("Alex2")
                .password("qwert")
                .role(Role.ADMIN)
                .build();
        userList.add(userRoleAdmin);
        assertAll(
                ()->Mockito.doReturn(userList).when(userRepository).findAll(),
                ()->Assertions.assertEquals(expectedUserList, service.getAll()),
                ()->Mockito.verify(userRepository, Mockito.times(1)).findAll()
        );
    }
    @Test
    void delete_whenExistUser_thenThereIsNoException() {
        service.delete(ID);
    }

    @Test
    void update_whenNoExistUser_thenThrowException_ResponseStatusException() {
        final User user = null;
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        UserDto userDtoUser = UserDto.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            service.update(ID, userDtoUser);
        });
        Assertions.assertEquals("User not found", thrown.getMessage());
    }
    @Test
    @DisplayName("нет исключений, если пользователь существует в БД и Должен единожды вызывать нужные методы")
    void update_whenExistUser_thenThereIsNoException() {
        UserDto userDtoUser = UserDto.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        User user = User.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .password("qwert")
                .role(Role.USER)
                .build();
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        Mockito.doReturn(user).when(userRepository).save(user);

        assertAll(
                ()-> Assertions.assertEquals(user, service.update(ID, userDtoUser)),
                () -> Mockito.verify(userRepository, Mockito.times(1)).findById(ID),
                () -> Mockito.verify(userRepository, Mockito.times(1)).save(user)
        );
    }

    @Test
    @DisplayName("нет исключений, если пользователь существует в БД и Должен единожды вызывать нужные методы")
    void update_byEmail_whenExistUser_thenThereIsNoException() {
        String email = "alex@gmail.com";
        UserDto userDtoUser = UserDto.builder()
                .id(1)
                .email(email)
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        User user = User.builder()
                .id(1)
                .email(email)
                .phone("8999777888123")
                .name("Alex")
                .password("qwert")
                .role(Role.USER)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
        Mockito.doReturn(user).when(userRepository).save(user);

        assertAll(
                () -> Assertions.assertEquals(user, service.update(email, userDtoUser)),
                () -> Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email),
                () -> Mockito.verify(userRepository, Mockito.times(1)).save(user)
        );
    }

    @Test
    void update_byEmail_whenNoExistUser_thenThrowException_ResponseStatusException() {
        String email = "alex@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(null));
        UserDto userDtoUser = UserDto.builder()
                .id(1)
                .email(email)
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            service.update(email, userDtoUser);
        });
        Assertions.assertEquals("User not found", thrown.getMessage());
    }

}