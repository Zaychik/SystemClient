package com.zaychik.learning.system_user_rest.utils;


import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public UserDto mapToProductDto(User entity){
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setPhone(entity.getPhone());
        return dto;
    }

}
