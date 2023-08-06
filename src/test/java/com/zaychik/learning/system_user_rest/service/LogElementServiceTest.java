package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.repository.LogElementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

class LogElementServiceTest {
    private LogElementRepository logElementRepository;
    private LogElementService service;
    private static final String USER_EMAIL = "admin@gmail.com";
    @BeforeEach
    void setUp() {
        logElementRepository = mock(LogElementRepository.class);
        service = new LogElementService(logElementRepository);
    }
    @Test
    void readAllbyUserEmail() {
        List<LogElement> logElementList = new LinkedList<LogElement>();
        LogElement logElement1 = LogElement.builder()
                .userEmail(USER_EMAIL)
                .url("/")
                .method("GET")
                .dtEvent(LocalDateTime.now())
                .build();

        logElementList.add(logElement1);
        LogElement logElement2 = LogElement.builder()
                .userEmail(USER_EMAIL)
                .url("/")
                .method("PUT")
                .dtEvent(LocalDateTime.now())
                .build();
        logElementList.add(logElement2);

        Mockito.doReturn(logElementList).when(logElementRepository).findAllByUserEmail(USER_EMAIL);
        assertAll(
                () -> Assertions.assertEquals(logElementList, service.readAllbyUserEmail(USER_EMAIL)),
                () -> Mockito.verify(logElementRepository, Mockito.times(1)).findAllByUserEmail(USER_EMAIL)
        );
    }

}