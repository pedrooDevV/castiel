package com.example.castiel.DTOs;

import com.example.castiel.Enums.Role;

public record RegisterRequestDTO(String nome, String senha, String email, Role role) {

}
