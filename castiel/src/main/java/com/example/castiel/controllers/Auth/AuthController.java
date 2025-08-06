package com.example.castiel.controllers.Auth;

import com.example.castiel.DTOs.LoginRequestDTO;
import com.example.castiel.DTOs.LoginResponseDTO;
import com.example.castiel.DTOs.RegisterRequestDTO;
import com.example.castiel.entities.AuthErpEntities.Usuario;
import com.example.castiel.repositories.UserRepository;
import com.example.castiel.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        Optional<Usuario> usuarioOpt = userRepository.findByNome(body.nome());

        if (usuarioOpt.isEmpty()) {
            String msg = "Usuário não encontrado";
            System.out.println(msg);  // Log no backend
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(msg, null));
        }
        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            String msg = "Senha incorreta";
            System.out.println(msg);  // Log no backend
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(msg, null));
        }

        String token = tokenService.generateToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(usuario.getNome(), token));
    }

    @PostMapping("adm/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> myUser = userRepository.findByEmail(body.email());

        if (myUser.isEmpty()) {
            Usuario user = new Usuario();
            user.setSenha(passwordEncoder.encode(body.senha()));
            user.setNome(body.nome());
            user.setEmail(body.email());
            user.setRole(body.role());  // corrigido

            userRepository.save(user);

            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponseDTO(user.getNome(), token));
        }

        return ResponseEntity.badRequest().body(new LoginResponseDTO("Usuário já existe", null));
    }

}
