package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.entity.User;
import com.zaychik.learning.system_user_rest.repository.LogElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogElementService {
    @Autowired
    private LogElementRepository logElementRepository;

}
