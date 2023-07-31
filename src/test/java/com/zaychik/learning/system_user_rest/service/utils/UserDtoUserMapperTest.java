package com.zaychik.learning.system_user_rest.service.utils;

import com.zaychik.learning.system_user_rest.model.Role;
import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoUserMapperTest {

    @Test
    void mapToUserDto() {
        User user = User.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .password("qwert")
                .role(Role.USER)
                .build();
        UserDto userDto = UserDtoUserMapper.INSTANCE.mapToUserDto(user);

        assertAll(
                () -> assertEquals(userDto.getId(), user.getId()),
                () -> assertEquals(userDto.getEmail(), user.getEmail()),
                () -> assertEquals(userDto.getPhone(), user.getPhone()),
                () -> assertEquals(userDto.getName(), user.getName()),
                () -> assertEquals(userDto.getRole(), user.getRole())
        );
    }

    @Test
    void mapToUser() {
        UserDto userDto = UserDto.builder()
                .id(1)
                .email("alex@gmail.com")
                .phone("8999777888123")
                .name("Alex")
                .role(Role.USER)
                .build();
        User user = UserDtoUserMapper.INSTANCE.mapToUser(userDto);
        assertAll(
                () -> assertEquals(userDto.getId(), user.getId()),
                () -> assertEquals(userDto.getEmail(), user.getEmail()),
                () -> assertEquals(userDto.getPhone(), user.getPhone()),
                () -> assertEquals(userDto.getName(), user.getName()),
                () -> assertEquals(userDto.getRole(), user.getRole()),
                () -> assertNull(user.getPassword())
        );
    }
}