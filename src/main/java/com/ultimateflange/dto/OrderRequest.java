package com.ultimateflange.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String product;
    private Integer quantity;
    private String size;
    private String material;
    private String specifications;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String address;
    private Long userId;
}