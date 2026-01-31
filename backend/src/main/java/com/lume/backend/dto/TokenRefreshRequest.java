package com.lume.backend.dto;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
