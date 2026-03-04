package com.ultimateflange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String token;
    private String message;

    public AuthResponse(Object id, Object email, Object firstName, Object lastName, String invalidEmailOrPassword) {
    }
}