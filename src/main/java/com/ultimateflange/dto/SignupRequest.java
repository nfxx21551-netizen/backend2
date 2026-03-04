package com.ultimateflange.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String company;
    private String phone;
    private String industry;
}
