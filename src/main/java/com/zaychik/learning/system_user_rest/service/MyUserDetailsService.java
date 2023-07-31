package com.zaychik.learning.system_user_rest.service;


import com.zaychik.learning.system_user_rest.model.auth.UserAuth;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
/**
 * Сервис - класс, используется для настройки безопасности - доступ через токен
 */
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    /**
     * Поиск пользователя по Username. В моем случае Username - это почта
     * @param username - почта клиента
     * @return объект класса UserDetails
     * @throws ResponseStatusException с HttpStatus.NOT_FOUND и текстом "User not found" если пользователя нет в БД
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserAuth(repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
