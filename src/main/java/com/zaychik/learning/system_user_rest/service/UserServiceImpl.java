package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean update(User user, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return userRepository.delete(id);
    }
}
