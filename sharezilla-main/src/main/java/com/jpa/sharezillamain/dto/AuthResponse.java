package com.jpa.sharezillamain.dto;

import lombok.Data;

// dto/AuthResponse.java
@Data
public class AuthResponse {
    private String token;
    public AuthResponse(String token) {
        this.token = token;
    }
    public String getToken() { return token; }
}
