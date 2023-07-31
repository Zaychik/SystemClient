package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
import com.zaychik.learning.system_user_rest.model.auth.RegisterRequest;
import com.zaychik.learning.system_user_rest.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контролер - точка входа для авторизации и регистрации новых клиентов.
 * Цель - получить токен. Единственное поле класса {@link AuthenticationResponce}
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    /**
     * Регистирация нового пользователя в системе
     * @param request - объект класса {@link RegisterRequest}
     * @return объект класса {@link AuthenticationResponce}
     * Возвращается 200 если пользователь сохранился в БД
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponce> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Авторизация пользователя в системе
     * @param request - объект класса {@link AuthenticationRequest}
     * @return объект класса {@link AuthenticationResponce}
     * Возвращается 200 если пользователь авторизовался в БД
     */
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponce> register (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
