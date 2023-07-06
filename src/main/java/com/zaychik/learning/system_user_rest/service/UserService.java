package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import com.zaychik.learning.system_user_rest.service.utils.UserDtoUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDtoUserMapper.INSTANCE::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "users_cache")
    public UserDto get(int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        return UserDtoUserMapper.INSTANCE.mapToUserDto(user.get());
    }

    @CacheEvict("users_cache")
    public void delete(int id) {
        CheckUserExistById(id);
        userRepository.deleteById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(int id, UserDto user) {
        CheckUserExistById(id);
        User userOld = userRepository.findById(id).get();
        User userNew = UserDtoUserMapper.INSTANCE.mapToUser(user);
        userNew.setPassword(userOld.getPassword());
        userNew.setId(userOld.getId());
        return userRepository.save(userNew);
    }

    private void CheckUserExistById(Integer userID) {
        if (!userRepository.existsById(userID)) {
            throw new IllegalArgumentException("Пользователя с таким номером не существует");
        }
    }
}
