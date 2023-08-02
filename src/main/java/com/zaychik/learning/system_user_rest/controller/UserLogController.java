package com.zaychik.learning.system_user_rest.controller;

import com.zaychik.learning.system_user_rest.model.LogElement;
import com.zaychik.learning.system_user_rest.service.LogElementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контролер - точка входа для получения логов
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserLogController {

    private final LogElementService logElementService;

    /**
     * Получение всего лога по клиенту
     * @param email - почта пользователя, по которому запрашивается информация
     * @return список объектов класса {@link LogElement}
     */
    @PostMapping("/log")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Получить лог по одному пользователю по email",
            notes = "Получить лог по одному пользователю по email")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Ошибка доступа, нужен свежий токен админа")
    })
    public List<LogElement> readLog(
            @ApiParam(value = "Электронная почта пользователя", required = true) @RequestParam String email
    ) {
        return logElementService.readAllbyUserEmail(email);
    }

}

