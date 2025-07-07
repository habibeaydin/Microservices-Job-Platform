package com.sau.authservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        //permitAll() → /api/auth/** altındaki endpoint'lere herkese erişim sağlar
                        .anyRequest().authenticated() //Diğer tüm endpoint'lere sadece kimliği doğrulanmış kullanıcı erişebilir
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    // filterChain → Güvenlik kurallarını ayarlıyor

    // passwordEncoder → Şifreleri encode etmek ve login sırasında çözmek için
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
