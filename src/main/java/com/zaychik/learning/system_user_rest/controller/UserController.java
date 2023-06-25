package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.entity.LogElement;
import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;
import com.zaychik.learning.system_user_rest.service.LogElementService;
import com.zaychik.learning.system_user_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LogElementService logElementService;
    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User create(@AuthenticationPrincipal User userAuth, @RequestBody User user) {
        logElementService.saveLogElement(
                LogElement.builder().
                        userEmail(userAuth.getEmail()).
                        url("/users").
                        method("post").
                        dtEvent(new Date()).
                        build()
        );
        return userService.create(user);
    }


    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> read(@AuthenticationPrincipal User userAuth) {
        logElementService.saveLogElement(
                LogElement.builder().
                        userEmail(userAuth.getEmail()).
                        url("/users").
                        method("get").
                        dtEvent(new Date()).
                        build()
        );
        return userService.readAll();
    }

    @GetMapping("/users/{id}")
    public UserDto read(@AuthenticationPrincipal User userAuth, @PathVariable(name = "id") int id) {
        logElementService.saveLogElement(
                LogElement.builder().
                        userEmail(userAuth.getEmail()).
                        url("/users/" + id).
                        method("get").
                        dtEvent(new Date()).
                        build()
        );

        return userService.read(id);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@AuthenticationPrincipal User userAuth, @PathVariable(name = "id") int id, @RequestBody User user) {
        logElementService.saveLogElement(
                LogElement.builder().
                        userEmail(userAuth.getEmail()).
                        url("/users/" + id).
                        method("put").
                        dtEvent(new Date()).
                        build()
        );
        user.setId(id);
        userService.update(user);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@AuthenticationPrincipal User userAuth, @PathVariable(name = "id") int id) {
        logElementService.saveLogElement(
                LogElement.builder().
                        userEmail(userAuth.getEmail()).
                        url("/users/" + id).
                        method("delete").
                        dtEvent(new Date()).
                        build()
        );
        userService.delete(id);
    }

    @PostMapping("/users/log")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<LogElement> readLog(@RequestBody User user) {

        return logElementService.readAllbyUserEmail(user.getEmail());
    }


}