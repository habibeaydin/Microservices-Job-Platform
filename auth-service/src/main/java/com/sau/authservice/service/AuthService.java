package com.sau.authservice.service;

import com.sau.authservice.dto.*;
import com.sau.authservice.exception.EmailAlreadyExistsException;
import com.sau.authservice.exception.InvalidCredentialsException;
import com.sau.authservice.feign.UserClient;
import com.sau.authservice.feign.dto.UserCreateRequest;
import com.sau.authservice.model.AuthUser;
import com.sau.authservice.repository.AuthRepository;
import com.sau.authservice.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserClient userClient;

    public RegisterResponse register(RegisterRequest request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        AuthUser user = AuthUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .companyName(request.getCompanyName())
                .build();

        authRepository.save(user);

        // Feign ile User-Service'e profil oluşturma isteği gönderme
        userClient.createUser(
                UserCreateRequest.builder()
                        .authUserId(user.getId())
                        .fullName(request.getFullName())
                        .role(user.getRole())
                        .phone(request.getPhone())
                        .companyName(request.getCompanyName())
                        .build()
        );

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(token)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        AuthUser user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
