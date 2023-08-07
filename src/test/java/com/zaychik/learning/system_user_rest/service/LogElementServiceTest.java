package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.repository.LogElementRepository;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

class LogElementServiceTest {
    private LogElementRepository logElementRepository = mock(LogElementRepository.class);
    private LogElementService service = new LogElementService(logElementRepository);
    private static final String USER_EMAIL = "admin@gmail.com";

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