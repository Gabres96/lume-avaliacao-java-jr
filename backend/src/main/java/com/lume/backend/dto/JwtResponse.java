package com.lume.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String refreshToken;
    private Long id;
    private String email;
    private String type = "Bearer";

    public JwtResponse(String token, String refreshToken, Long id, String email) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.email = email;
    }
}