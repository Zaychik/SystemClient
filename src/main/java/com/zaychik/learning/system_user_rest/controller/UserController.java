package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.model.auth.UserAuth;
import com.zaychik.learning.system_user_rest.service.LogElementService;
import com.zaychik.learning.system_user_rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Контролер - точка входа для управления учетным записями
 */

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LogElementService logElementService;

    /**
     * Получение всех пользователей в системе
     * @param userAuth - объект {@link UserAuth} содержит информацию авторизованного пользователя
     * @param request - запрос, который приходит сервису
     * @return список объектов класса {@link UserDto}     *
     * Доступ только у администратора
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> read(@AuthenticationPrincipal UserAuth userAuth,
                              HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        return userService.getAll();
    }

    /**
     * Получение конкретного пользователя в системе по ID
     * @param userAuth - объект {@link UserAuth} содержит информацию авторизованного пользователя
     * @param id - номер(внутренний номер) пользователя, по которому запрашивается информация
     * @param request - запрос, который приходит сервису
     * @return объект класса {@link UserDto}
     */
    @GetMapping("/{id}")
    public UserDto read(@AuthenticationPrincipal UserAuth userAuth,
                        @PathVariable int id,
                        HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        return userService.get(id);
    }

    /**
     * Получение конкретного пользователя в системе по email
     * @param userAuth - объект {@link UserAuth} содержит информацию авторизованного пользователя
     * @param email - номер(внутренний номер) пользователя, по которому запрашивается информация
     * @param request - запрос, который приходит сервису
     * @return объект класса {@link UserDto}
     */
    @GetMapping("/email")
    public UserDto read(@AuthenticationPrincipal UserAuth userAuth,
                        @RequestParam String email,
                        HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        return userService.get(email);
    }

    /**
     * Обновление информации конкретного пользователя в системе по ID
     * @param userAuth - объект {@link UserAuth} содержит информацию авторизованного пользователя
     * @param id - номер(внутренний номер) пользователя, по которому запрашивается информация
     * @param user - объект класса {@link UserDto}, содержит новые значения для объекта по номеру ID
     * @param request - запрос, который приходит сервису
     * Доступ только у администратора
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@AuthenticationPrincipal UserAuth userAuth,
                       @PathVariable int id,
                       @RequestBody UserDto user,
                       HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        userService.update(id, user);
    }

    /**
     * Обновление информации конкретного пользователя в системе по email
     * @param userAuth - объект {@link UserAuth} содержит информацию авторизованного пользователя
     * @param email - номер(внутренний номер) пользователя, по которому запрашивается информация
     * @param user - объект класса {@link UserDto}, содержит новые значения для объекта по номеру ID
     * @param request - запрос, который приходит сервису
     * Доступ только у администратора
     */
    @PutMapping("/email")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@AuthenticationPrincipal UserAuth userAuth,
                       @RequestParam String email,
                       @RequestBody UserDto user,
                       HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        userService.update(email, user);
    }
    /**
     * Удаление конкретного пользователя в системе по ID
     * @param userAuth - объект {@link UserAuth} содержит информацию авторизованного пользователя
     * @param id - номер(внутренний номер) пользователя, которого необходимо удалить
     * @param request - запрос, который приходит сервису
     * Доступ только у администратора
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@AuthenticationPrincipal UserAuth userAuth,
                       @PathVariable int id,
                       HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        userService.delete(id);
    }
}