package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.entity.LogElement;
import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.entity.UserDto;
import com.zaychik.learning.system_user_rest.repository.LogElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogElementService {
    @Autowired
    private LogElementRepository logElementRepository;

    public LogElement saveLogElement(LogElement logElement) {
        return logElementRepository.save(logElement);
    }

    public List<LogElement> readAll() {
        return (List<LogElement>) logElementRepository.findAll();
    }

    public List<LogElement> readAllbyUserEmail(String email) {
        return logElementRepository.findAllByUserEmail(email);
    }

}
