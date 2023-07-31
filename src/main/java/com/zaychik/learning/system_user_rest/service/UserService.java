package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.controller.exception.NotFoundException;
import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import com.zaychik.learning.system_user_rest.service.utils.UserDtoUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис - класс, сервисного слоя класса {@link User}
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Получение всех пользователей
     * @return список объектов класса {@link UserDto}
     */
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDtoUserMapper.INSTANCE::mapToUserDto)
                .collect(Collectors.toList());
    }
    /**
     * Получение одного пользователей по номеру ID.
     * @param id - внутренний идентификатор таблицы
     * @return объект класса {@link UserDto} Значение может быть получено из кэша "users_cache"
     */
    @Cacheable(value = "users_cache")
    public UserDto get(int id) {
        return UserDtoUserMapper.INSTANCE.mapToUserDto(getUserByID(id));
    }

    /**
     * Получение одного пользователей по email.
     * @param email - внутренний идентификатор таблицы
     * @throws NotFoundException  и текстом "User not found" если пользователя нет в БД
     * @return объект класса {@link UserDto} Значение может быть получено из кэша "users_cache"
     */
    @Cacheable(value = "users_cache")
    public UserDto get(String email) {
        return UserDtoUserMapper.INSTANCE.mapToUserDto(getUserByEmail(email));
    }

    /**
     * Удаление одного пользователей по номеру ID.
     * @param id - внутренний идентификатор таблицы
     * Удаляется также информация из кэша "users_cache"
     */
    @CacheEvict("users_cache")
    public void delete(int id) {
        userRepository.deleteById(id);
    }
    /**
     * Обновление одного пользователей по номеру ID.
     * @param id - внутренний идентификатор таблицы
     * @param user - объект класса {@link UserDto}, содержит новые значения для объекта по номеру ID
     * Удаляется также информация из кэша "users_cache"
     */
    public User update(int id, UserDto user)  {
        User userOld = getUserByID(id);
        User userNew = UserDtoUserMapper.INSTANCE.mapToUser(user);
        userNew.setPassword(userOld.getPassword());
        userNew.setId(userOld.getId());
        return userRepository.save(userNew);
    }

    /**
     * Обновление одного пользователей по email.
     * @param email - почта пользователя
     * @param user - объект класса {@link UserDto}, содержит новые значения для объекта по номеру ID
     * Удаляется также информация из кэша "users_cache"
     */
    public User update(String email, UserDto user)  {
        User userOld = getUserByEmail(email);
        User userNew = UserDtoUserMapper.INSTANCE.mapToUser(user);
        userNew.setPassword(userOld.getPassword());
        userNew.setId(userOld.getId());
        return userRepository.save(userNew);
    }

    /**
     * Проверка на существоание в БД пользотваеля по номеру ID.
     * @param userID - внутренний идентификатор таблицы
     * @throws NotFoundException  и текстом "User not found" если пользователя нет в БД
     */
    private User getUserByID(Integer userID) {
        Optional<User> user = userRepository.findById(userID);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }
    /**
     * Проверка на существоание в БД пользотваеля по номеру email.
     * @param email - почта пользователя
     * @throws NotFoundException  и текстом "User not found" если пользователя нет в БД
     */
    private User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

}
