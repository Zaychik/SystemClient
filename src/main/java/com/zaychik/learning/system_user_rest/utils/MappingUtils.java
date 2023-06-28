package com.zaychik.learning.system_user_rest.utils;


import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public UserDto mapToUserDto(User entity){
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .role(entity.getRole())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }
    public User mapToUser(UserDto entity){
        return User.builder()
                .name(entity.getName())
                .role(entity.getRole())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }
}
