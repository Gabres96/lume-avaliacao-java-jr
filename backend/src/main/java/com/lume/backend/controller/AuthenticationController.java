package com.lume.backend.controller;

import com.lume.backend.dto.*;
import com.lume.backend.entity.RefreshToken;
import com.lume.backend.entity.User;
import com.lume.backend.repository.UserRepository;
import com.lume.backend.security.JwtUtils;
import com.lume.backend.service.AuthenticationService;
import com.lume.backend.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtils jwtUtils,
                                    RefreshTokenService refreshTokenService,
                                    AuthenticationService authenticationService,
                                    UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String jwt = jwtUtils.generateJwtToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new AuthenticationResponse(
                jwt,
                refreshToken.getToken(),
                "Bearer",
                user.getId(),
                user.getEmail()
        ));
    }


    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado no banco ou expirado!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        org.springframework.security.core.Authentication authentication =
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        userRepository.findByEmail(email).ifPresent(user -> {
            refreshTokenService.deleteByUserId(user.getId());
        });

        return ResponseEntity.ok("Logout realizado com sucesso!");
    }
}