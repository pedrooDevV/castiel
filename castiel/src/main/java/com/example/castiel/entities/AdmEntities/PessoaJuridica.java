package com.example.castiel.entities.AdmEntities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("JURIDICA")
@Table(name = "pessoa_juridica")
public class PessoaJuridica {

    @NotBlank
    @Size(max = 18)
    private String cnpj;

    @NotBlank
    @Size(max = 100)
    private String razao_social;
}
