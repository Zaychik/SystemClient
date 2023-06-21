package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
/*
    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody User client) {
        userService.create(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    */

    @GetMapping(value = "/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.readAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final User user = userService.read(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody User user) {
        User client = userService.read(id);
        final boolean updated = userService.saveUser(user);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}