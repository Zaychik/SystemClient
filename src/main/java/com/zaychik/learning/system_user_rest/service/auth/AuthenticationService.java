package com.zaychik.learning.system_user_rest.service.auth;

import com.zaychik.learning.system_user_rest.model.auth.RegisterRequest;
import com.zaychik.learning.system_user_rest.model.Role;
import com.zaychik.learning.system_user_rest.model.User;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationRequest;
import com.zaychik.learning.system_user_rest.model.auth.AuthenticationResponce;
import com.zaychik.learning.system_user_rest.model.auth.UserAuth;
import com.zaychik.learning.system_user_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.testcontainers.shaded.org.apache.commons.lang3.BooleanUtils.or;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponce register(RegisterRequest request) {
        CheckPassword(request);
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode((request.getPassword())))
                .role(Role.USER)
                .build();
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        var jwtToken = jwtService.generateToken(new UserAuth(user));
        return AuthenticationResponce.builder()
                .token(jwtToken)
                .build();
    }

    private void CheckPassword(RegisterRequest request) {
        String password = request.getPassword();
        if (password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter a password");
        }
    }

    public AuthenticationResponce authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(new UserAuth(user));
        return AuthenticationResponce.builder()
                .token(jwtToken)
                .build();
    }
}
