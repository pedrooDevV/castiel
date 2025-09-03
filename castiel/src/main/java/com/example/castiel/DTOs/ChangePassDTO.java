package com.example.castiel.DTOs;

public record ChangePassDTO(
        Long userId,
        String newPassword
) {
}
