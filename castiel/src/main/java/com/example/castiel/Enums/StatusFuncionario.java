package com.example.castiel.Enums;

import lombok.Getter;

@Getter
public enum StatusFuncionario {
    ATIVO("A"),
    DESATIVADO("D");

    private final String codigo;

    StatusFuncionario(String codigo) {
        this.codigo = codigo;
    }

    public static StatusFuncionario fromCodigo(String codigo) throws IllegalAccessException {
        for(StatusFuncionario status : values()){
            if (status.getCodigo().equalsIgnoreCase(codigo)){
                return status;
            }
        }
        throw  new IllegalAccessException("Codigo invalido! " + codigo);
    }

}
