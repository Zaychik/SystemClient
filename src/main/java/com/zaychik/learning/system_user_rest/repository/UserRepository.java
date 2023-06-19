package com.zaychik.learning.system_user_rest.repository;

import com.zaychik.learning.system_user_rest.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(int id);

    User create(User user);
    boolean delete(int id);


}
