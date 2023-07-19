package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.service.LogElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контролер - точка входа для получения логов
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserLogController {

    private final LogElementService logElementService;

    /**
     * Получение всего лога по клиенту
     * @param user - объект {@link UserDto}, который расположен в теле запроса.
     * @return список объектов класса {@link LogElement}
     */
    @PostMapping("/log")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<LogElement> readLog(@RequestBody UserDto user) {
        return logElementService.readAllbyUserEmail(user.getEmail());
    }

}

