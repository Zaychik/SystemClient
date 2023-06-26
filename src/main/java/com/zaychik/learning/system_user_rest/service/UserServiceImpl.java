package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import com.zaychik.learning.system_user_rest.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public List<UserDto> readAll() {
        return userRepository.findAll().stream()
                .map(mappingUtils::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "users_cache")
    public UserDto read(int id) {
        return mappingUtils.mapToProductDto(userRepository.findById(id).get());
    }

    @Override
    @CachePut("users_cache")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @CacheEvict("users_cache")
    public void delete(int id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Пользователя с таким номером не существует");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User create(User user) {
        return saveUser(user);
    }

    @Override
    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("Пользователя с таким номером не существует");
        }
        return saveUser(user);
    }
}
