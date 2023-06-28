package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import com.zaychik.learning.system_user_rest.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(mappingUtils::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "users_cache")
    public UserDto get(int id) {
        return mappingUtils.mapToUserDto(userRepository.findById(id).get());
    }

    @CachePut("users_cache")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict("users_cache")
    public void delete(int id) {
        CheckUserExistById(id);
        userRepository.deleteById(id);
    }

    public User create(User user) {
        return saveUser(user);
    }

    public User update(int id, UserDto user) {
        CheckUserExistById(id);
        User userOld = userRepository.findById(id).get();
        User userNew = mappingUtils.mapToUser(user);
        userNew.setPassword(userOld.getPassword());
        userNew.setId(userOld.getId());
        return saveUser(userNew);
    }

    private void CheckUserExistById(Integer user) {
        if (!userRepository.existsById(user)) {
            throw new IllegalArgumentException("Пользователя с таким номером не существует");
        }
    }
}
