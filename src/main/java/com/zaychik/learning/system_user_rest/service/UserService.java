package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> readAll();

    User read(int id);

    boolean update(User user, int id);

    boolean delete(int id);
}