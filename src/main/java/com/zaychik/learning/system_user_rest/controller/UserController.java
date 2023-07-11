package com.zaychik.learning.system_user_rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.model.auth.UserAuth;
import com.zaychik.learning.system_user_rest.service.LogElementService;
import com.zaychik.learning.system_user_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LogElementService logElementService;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> read(@AuthenticationPrincipal UserAuth userAuth,
                              HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto read(@AuthenticationPrincipal UserAuth userAuth,
                        @PathVariable int id,
                        HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        return userService.get(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@AuthenticationPrincipal UserAuth userAuth,
                       @PathVariable int id,
                       @RequestBody UserDto user,
                       HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@AuthenticationPrincipal UserAuth userAuth,
                       @PathVariable int id,
                       HttpServletRequest request) {
        logElementService.logPush(userAuth, request);
        userService.delete(id);
    }
}