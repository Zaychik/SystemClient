package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> readAll();

    UserDto read(int id);

    User saveUser(User user);


    void delete(int id);

    User create(User user);

    User update(User user);
}