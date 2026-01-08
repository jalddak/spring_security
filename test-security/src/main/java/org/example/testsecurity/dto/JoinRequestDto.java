package org.example.testsecurity.dto;

import lombok.Data;

@Data
public class JoinRequestDto {
    private String username;
    private String password;
    private String role;
}
