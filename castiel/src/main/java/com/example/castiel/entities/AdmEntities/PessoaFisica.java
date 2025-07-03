package com.example.castiel.entities.AdmEntities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @NotBlank
    @Column(unique = true)
    @Size(max = 14)
    private String cpf;

    @NotNull
    LocalDate data_nascimento;
}
