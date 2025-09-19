package com.example.castiel.Enums;

public enum Tipo {
    ENTRADA1,
    SAIDA,
    ENTRADA2,
    SAIDA_FINAL;


    public boolean isEntrada() {
        return this == ENTRADA1 || this == ENTRADA2;
    }

    public boolean isSaida() {
        return this == SAIDA || this == SAIDA_FINAL;
    }


    }


