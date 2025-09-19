package com.example.castiel.DTOs;

import com.example.castiel.Enums.Tipo;

import java.time.LocalDateTime;

public record PontoEletronicoDTO(

        String cpf,
        String nome,
        String pedro, LocalDateTime dataHora,
        long horasTrabalhadas, Tipo tipo


) {
}
