package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.service.LogElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserLogController {
    @Autowired
    private LogElementService logElementService;

    @PostMapping("/log")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<LogElement> readLog(@RequestBody UserDto user) {
        return logElementService.readAllbyUserEmail(user.getEmail());
    }

}

