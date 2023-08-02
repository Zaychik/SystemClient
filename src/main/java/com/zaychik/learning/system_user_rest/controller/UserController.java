package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.model.auth.UserAuth;
import com.zaychik.learning.system_user_rest.service.LogElementService;
import com.zaychik.learning.system_user_rest.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Получить информацию по всем пользователям",
            notes = "Получить информацию по всем пользователям, необходима авторизация админа")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Ошибка доступа, нужен токен админа") })
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
    @ApiOperation(value = "Получить информацию по одному пользователю",
            notes = "Получить информацию по одному пользователю по внутреннему номеру")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Ошибка доступа, нужен свежий токен любой роли"),
            @ApiResponse(code = 404, message = "Нет пользователя под таким номером")
    }
    )
    public UserDto read(@AuthenticationPrincipal UserAuth userAuth,
                        @ApiParam(value = "Внутренний номер пользователя", required = true) @PathVariable int id,
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
    @ApiOperation(value = "Получить информацию по одному пользователю по email",
            notes = "Получить информацию по одному пользователю по email")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Ошибка доступа, нужен свежий токен любой роли"),
            @ApiResponse(code = 404, message = "Нет пользователя c таким email")
    })
    public UserDto read(@AuthenticationPrincipal UserAuth userAuth,
                        @ApiParam(value = "Электронная почта пользователя", required = true) @RequestParam String email,
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
    @ApiOperation(value = "Обновить информацию по одному пользователю",
            notes = "Обновить информацию по одному пользователю по внутреннему номеру")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ошибка обновления, возможно ошибка БД."),
            @ApiResponse(code = 403, message = "Ошибка доступа, нужен свежий токен админа"),
            @ApiResponse(code = 404, message = "Нет пользователя c таким внутренним номером")
    })
    public void update(@AuthenticationPrincipal UserAuth userAuth,
                       @ApiParam(value = "Внутренний номер пользователя", required = true) @PathVariable int id,
                       @ApiParam(value = "Обновленная информация пользователя", required = true) @RequestBody UserDto user,
                       HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        userService.update(id, user);
    }

    /**
     * Обновление информации конкретного пользователя в системе по email
     * @param userAuth - объект {@link UserAuth} содержит информацию авторизованного пользователя
     * @param email - почта пользователя, по которому запрашивается информация
     * @param user - объект класса {@link UserDto}, содержит новые значения для объекта по номеру email
     * @param request - запрос, который приходит сервису
     * Доступ только у администратора
     */
    @PutMapping("/email")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Обновить информацию по одному пользователю по email",
            notes = "Обновить информацию по одному пользователю по email")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ошибка обновления, возможно ошибка БД."),
            @ApiResponse(code = 403, message = "Ошибка доступа, нужен свежий токен админа"),
            @ApiResponse(code = 404, message = "Нет пользователя c таким email")
    })
    public void update(@AuthenticationPrincipal UserAuth userAuth,
                       @ApiParam(value = "Электронная почта пользователя", required = true) @RequestParam String email,
                       @ApiParam(value = "Обновленная информация пользователя", required = true) @RequestBody UserDto user,
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
    @ApiOperation(value = "Удалить пользователя из БД",
            notes = "ОУдалить пользователя из БД по внутреннему номеру")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ошибка обновления, возможно ошибка БД."),
            @ApiResponse(code = 403, message = "Ошибка доступа, нужен свежий токен админа"),
            @ApiResponse(code = 404, message = "Нет пользователя c таким внутренним номером")
    })
    public void delete(@AuthenticationPrincipal UserAuth userAuth,
                       @ApiParam(value = "Внутренний номер пользователя", required = true) @PathVariable int id,
                       HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        userService.delete(id);
    }
}