package com.lume.backend.config;

import com.lume.backend.entity.Role;
import com.lume.backend.entity.User;
import com.lume.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByEmail("admin@admin.com").isEmpty()) {

                User admin = User.builder()
                        .name("Admin")
                        .email("admin@admin.com")
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
