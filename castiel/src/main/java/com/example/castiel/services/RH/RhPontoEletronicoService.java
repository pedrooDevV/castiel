package com.example.castiel.services.RH;

import com.example.castiel.DTOs.PontoEletronicoDTO;
import com.example.castiel.Enums.Tipo;
import com.example.castiel.entities.RhEntities.PontoEletronico;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@Service
public class RhPontoEletronicoService {

    public void RegistrarPonto(PontoEletronicoDTO pontoEletronicoDTO){



    }



    public Tipo descobrirProximoPonto(List<PontoEletronico> registro){
        if(registro.isEmpty()){
            return Tipo.ENTRADA1;
        }

        registro.sort(Comparator.comparing(PontoEletronico::getDataHora));
        Tipo ultimo = registro.getLast().getTipo();

        return switch(ultimo){
            case ENTRADA1 -> Tipo.SAIDA;
            case SAIDA -> Tipo.ENTRADA1;
            case ALMOCO -> Tipo.ENTRADA2;
            case ENTRADA2 -> Tipo.SAIDA2;
            default -> throw new RuntimeException("Todas batidas realizadas hoje, informe o RH em caso de erro ou confusão ");

        };

    }
}
