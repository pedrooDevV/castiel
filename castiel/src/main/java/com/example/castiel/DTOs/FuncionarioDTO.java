package com.example.castiel.DTOs;

import java.time.LocalDate;

public record FuncionarioDTO(

        String cargo,
        String nome,
        String cpf,
        LocalDate dataAdmissao,
        LocalDate dataDemissao,
        Double salario

) {
}
