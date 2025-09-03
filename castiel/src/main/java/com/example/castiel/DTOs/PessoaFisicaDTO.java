package com.example.castiel.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record PessoaFisicaDTO(
        String nome,
        String cpf,
        @JsonProperty("rg")
        String pessoaRg,
        String nacionalidade,
        String email,
        LocalDate data_nascimento
) {
}
