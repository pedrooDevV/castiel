package com.example.castiel.entities.AuthErpEntities;


import com.example.castiel.Enums.Role;
import com.example.castiel.entities.RhEntities.PessoaFisica;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Data
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotBlank
    @Column(name = "cpf",unique = true)
    private String cpf;

    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    private String senha;

    @CreatedDate
    @Column(name = "dt_cadastro")
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PessoaFisica pessoaFisica;

    private LocalDate ultimaTrocaSenha;

}
