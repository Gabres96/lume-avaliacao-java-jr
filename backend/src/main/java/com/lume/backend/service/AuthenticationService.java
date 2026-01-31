package com.lume.backend.service;

import com.lume.backend.dto.AuthenticationResponse;
import com.lume.backend.dto.RegisterRequest;
import com.lume.backend.entity.User;
import com.lume.backend.repository.UserRepository;
import com.lume.backend.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String tokenGerado = jwtUtils.generateJwtToken(user);

        return new AuthenticationResponse(
                tokenGerado,
                java.util.UUID.randomUUID().toString(),
                "Bearer",
                user.getId(),
                user.getEmail()
        );
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));
    }
}