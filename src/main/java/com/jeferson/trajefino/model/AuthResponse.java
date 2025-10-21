package com.jeferson.trajefino.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String userName;
    private String fullName;

    public AuthResponse(String token, String userName, String fullName) {
        this.token = token;
        this.userName = userName;
        this.fullName = fullName;
    }
}
