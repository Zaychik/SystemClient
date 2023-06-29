package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.entity.LogElement;
import com.zaychik.learning.system_user_rest.entity.auth.UserAuth;
import com.zaychik.learning.system_user_rest.repository.LogElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogElementService {
    @Autowired
    private LogElementRepository logElementRepository;

    public LogElement logPush(UserAuth userAuth, HttpServletRequest request) {
        return logElementRepository.save(
                LogElement.builder().
                        userEmail(userAuth.getUsername()).
                        url(String.valueOf(request.getRequestURL())).
                        method(request.getMethod()).
                        dtEvent(LocalDateTime.now()).
                        build()
        );
    }

    public List<LogElement> readAll() {
        return (List<LogElement>) logElementRepository.findAll();
    }

    public List<LogElement> readAllbyUserEmail(String email) {
        return logElementRepository.findAllByUserEmail(email);
    }

}
