package com.zaychik.learning.system_user_rest.service;

import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.UserDto;
import com.zaychik.learning.system_user_rest.model.auth.UserAuth;
import com.zaychik.learning.system_user_rest.repository.LogElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис - класс, сервисного слоя класса {@link LogElement}
 */
@Service
@RequiredArgsConstructor
public class LogElementService {
    private final LogElementRepository logElementRepository;

    /**
     * Сохранение {@link LogElement} в базу данных.
     * @param userAuth - пользователь, который делает REST запрос {@link UserAuth}
     * @param request - запрос, который приходит сервису
     * @return объект класса {@link LogElement}, который сохранился в БД
     */
    public LogElement logPush(UserAuth userAuth, HttpServletRequest request) {
        return logElementRepository.save(
                LogElement.builder()
                        .userEmail(userAuth.getUsername())
                        .url(String.valueOf(request.getRequestURL()))
                        .method(request.getMethod())
                        .dtEvent(LocalDateTime.now())
                        .build()
        );
    }
    /**
     * Получение всего лога по клиенту
     * @param email - почта клиента, по которому нужно получить лог
     * @return список объектов класса {@link LogElement}
     */
    public List<LogElement> readAllbyUserEmail(String email) {
        return logElementRepository.findAllByUserEmail(email);
    }

}
