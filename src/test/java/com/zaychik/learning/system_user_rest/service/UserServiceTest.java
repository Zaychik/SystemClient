package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.entity.Role;
import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import com.zaychik.learning.system_user_rest.service.utils.UserDtoUserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;
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
    void update() {
    }

    @Test
    void get() {
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
}