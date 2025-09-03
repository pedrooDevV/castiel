package com.example.castiel.controllers.Auth;

import com.example.castiel.DTOs.ChangePassDTO;
import com.example.castiel.DTOs.LoginRequestDTO;
import com.example.castiel.DTOs.LoginResponseDTO;
import com.example.castiel.DTOs.RegisterRequestDTO;
import com.example.castiel.entities.AuthErpEntities.Usuario;
import com.example.castiel.repositories.UserRepository;
import com.example.castiel.services.Security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
        Optional<Usuario> usuarioOpt = userRepository.findByNome(body.nome());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO("Usuário não encontrado", null));
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO("Senha incorreta", null));
        }

        if (usuario.getUltimaTrocaSenha() == null
                || usuario.getUltimaTrocaSenha().isBefore(LocalDate.now().minusDays(60))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginResponseDTO("Senha expirada, contate o administrador", null));
        }

        String token = tokenService.gerarToken(usuario.getNome());
        return ResponseEntity.ok(new LoginResponseDTO(usuario.getNome(), token));
    }

    @PostMapping("/adm/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> usuarioExistente = userRepository.findByCpf(body.cpf());

        if (usuarioExistente.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponseDTO("Usuário já existe", null));
        }

        Usuario user = new Usuario();
        user.setNome(body.nome());
        user.setEmail(body.email());
        user.setSenha(passwordEncoder.encode(body.senha()));
        user.setUltimaTrocaSenha(LocalDate.now());
        user.setCpf(body.cpf());
        user.setRole(body.role());

        userRepository.save(user);

        String token = tokenService.gerarToken(user.getNome());

        return ResponseEntity.ok(new LoginResponseDTO(user.getNome(), token));
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassDTO dto, @PathVariable Long id) {
        Usuario usuario = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(dto.newPassword()));
        usuario.setUltimaTrocaSenha(LocalDate.now());
        userRepository.save(usuario);

        return ResponseEntity.ok("Senha atualizada com sucesso");
    }
}
