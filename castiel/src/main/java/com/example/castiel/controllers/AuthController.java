package com.example.castiel.controllers;

import com.example.castiel.DTOs.LoginRequestDTO;
import com.example.castiel.DTOs.LoginResponseDTO;
import com.example.castiel.entities.AuthErpEntities.Usuario;
import com.example.castiel.repositories.UserRepository;
import com.example.castiel.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
        Usuario usuario = userRepository.findByEmail(body.email())
                .orElseThrow(() ->new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (passwordEncoder.matches(body.password(), usuario.getSenha())) {
            String token = tokenService.generateToken(usuario);
            return ResponseEntity.ok(new LoginResponseDTO(usuario.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
