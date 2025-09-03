package com.example.castiel.entities.RhEntities;

import com.example.castiel.entities.AuthErpEntities.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank(message = "A pessoa deve ter CPF")
    @Size(max = 14)
    @Column(unique = true)
    private String cpf;

    @NotBlank(message = "A pessoa deve ter RG")
    @Size(max = 8)
    @Column(name = "rg", unique = true)
    private String rg;

    @Column(name = "nacionalidade", nullable = false)
    private String nacionalidade;

    @NotBlank
    @Size(max = 150)
    private String email;

    @NotNull(message = "A pessoa deve ter data de nascimento")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data_nascimento;

    @Column(name = "tipo_sanguinio")
    @Size(max = 3)
    private String tipoSanguineo;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @OneToOne(mappedBy = "pessoaFisica", cascade = CascadeType.ALL)
    private Funcionario funcionario;
}
