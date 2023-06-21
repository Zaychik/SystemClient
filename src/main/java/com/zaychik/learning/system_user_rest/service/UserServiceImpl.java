package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean update(User user, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        userRepository.deleteById(id);
        return true;
    }
}
