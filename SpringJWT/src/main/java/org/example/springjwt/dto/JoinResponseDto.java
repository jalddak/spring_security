package org.example.springjwt.dto;

import lombok.Data;
import org.example.springjwt.entity.UserEntity;

@Data
public class JoinResponseDto {
    private int id;
    private String username;
    private String role;

    private JoinResponseDto(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public static JoinResponseDto from(UserEntity user) {
        return new JoinResponseDto(user.getId(), user.getUsername(), user.getRole());
    }
}

