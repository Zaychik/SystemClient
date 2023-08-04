package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.repository.LogElementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

class LogElementServiceTest {
    AutoCloseable openMocks;
    @Mock
    private LogElementRepository logElementRepository;
    @InjectMocks
    private LogElementService service;
    private static final String USER_EMAIL = "admin@gmail.com";
    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
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
    @AfterEach
    void tearDown() throws Exception {
        // my tear down code...
        openMocks.close();
    }
}