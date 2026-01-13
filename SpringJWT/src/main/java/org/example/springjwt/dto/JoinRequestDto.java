package org.example.springjwt.dto;

import lombok.Data;

@Data
public class JoinRequestDto {
    private String username;
    private String password;
    private String role;
}
