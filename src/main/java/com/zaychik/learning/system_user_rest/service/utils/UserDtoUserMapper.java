package com.zaychik.learning.system_user_rest.service.utils;

import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoUserMapper {
    UserDtoUserMapper INSTANCE = Mappers.getMapper(UserDtoUserMapper.class);
    UserDto mapToUserDto(User user);
    User mapToUser(UserDto userDto);
}
