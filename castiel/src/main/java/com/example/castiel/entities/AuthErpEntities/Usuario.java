package com.example.castiel.entities.AuthErpEntities;


import com.example.castiel.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(unique = true)
    private String nome;

    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // padrão USER

    public void getRole(Role role) {
    }
}
