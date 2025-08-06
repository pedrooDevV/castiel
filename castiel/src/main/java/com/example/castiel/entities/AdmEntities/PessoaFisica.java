package com.example.castiel.entities.AdmEntities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@DiscriminatorValue("FISICA")
@Table(name = "pessoa_fisica")
public class PessoaFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A pessoa deve ter cpf")
    @Column(unique = true)
    @Size(max = 14)
    private String cpf;

    @NotBlank(message = "A pessoa deve ter RG")
    @Column(name = "rg")
    @Size(max = 8)
    private String pessoaRg;

    @NotBlank(message = "A pessoa deve ter nacionalidade")
    @Size(max = 80)
    private String nacionalide;

    @NotBlank
    @Size(max = 150)
    private String email;

    @NotNull(message = "A pessoa deve ter data de nascimento")
    LocalDate data_nascimento;
}
