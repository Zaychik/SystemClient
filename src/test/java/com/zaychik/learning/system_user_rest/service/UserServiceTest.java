package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.Role;
import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import com.zaychik.learning.system_user_rest.service.utils.UserDtoUserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final int ID = 1;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDtoUserMapper mapper = Mappers.getMapper(UserDtoUserMapper.class);
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
        when(userRepository.existsById(ID)).thenReturn(true);
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        UserDto actualuser = service.get(ID);
        Mockito.verify(userRepository, Mockito.times(1)).findById(ID);
        Assertions.assertEquals(userDto, actualuser);
    }

    @Test
    void get_whenNoExistUser_thereIsException() {
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
        when(userRepository.existsById(ID)).thenReturn(false);
        ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.get(ID);
        });
        Assertions.assertEquals("404 NOT_FOUND \"User not found\"", thrown.getMessage());
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

        Mockito.doReturn(userList).when(userRepository).findAll();
        Assertions.assertEquals(expectedUserList, service.getAll());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void create() {
        final User user = User.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .password("qwert")
                .role(Role.USER)
                .build();
        Mockito.doReturn(user).when(userRepository).save(user);
        Assertions.assertEquals(user, service.create(user));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }


    @Test
    void delete_whenExistUser_thenThereIsNoException() {
        when(userRepository.existsById(ID)).thenReturn(true);
        service.delete(ID);
        Mockito.verify(userRepository, Mockito.times(1)).existsById(ID);
    }

    @Test
    void delete_whenNoExistUser_thenThrowException_ResponseStatusException() {
        when(userRepository.existsById(ID)).thenReturn(false);
        ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.delete(ID);
        });
        Assertions.assertEquals("404 NOT_FOUND \"User not found\"", thrown.getMessage());
    }

    @Test
    void update_whenNoExistUser_thenThrowException_ResponseStatusException() {
        when(userRepository.existsById(ID)).thenReturn(false);
        UserDto userDtoUser = UserDto.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.update(ID, userDtoUser);
        });
        Assertions.assertEquals("404 NOT_FOUND \"User not found\"", thrown.getMessage());
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
        when(userRepository.existsById(ID)).thenReturn(true);
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(user));
        Mockito.doReturn(user).when(userRepository).save(user);
        Assertions.assertEquals(user, service.update(ID, userDtoUser));
        Mockito.verify(userRepository, Mockito.times(1)).existsById(ID);
        Mockito.verify(userRepository, Mockito.times(1)).findById(ID);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }
}