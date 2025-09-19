package com.example.castiel.services.RH;

import com.example.castiel.DTOs.PontoEletronicoDTO;
import com.example.castiel.Enums.Tipo;
import com.example.castiel.entities.RhEntities.PontoEletronico;
import com.example.castiel.repositories.PontoEletronicoRepository;
import com.example.castiel.repositories.pessoas.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class RhPontoEletronicoService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PontoEletronicoRepository pontoEletronicoRepository;

    private static final boolean RESET_MESMO_DIA = false;

    public PontoEletronicoDTO RegistrarPonto(PontoEletronicoDTO pontoEletronicoDTO) {
        var funcionario = funcionarioRepository.findByCpf(pontoEletronicoDTO.cpf())
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado com esse CPF" + pontoEletronicoDTO.cpf()));

        LocalDateTime data = LocalDateTime.now();

        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fim = LocalDate.now().atTime(LocalTime.MAX);

        List<PontoEletronico> registros = pontoEletronicoRepository.buscarBatidasDoDia(funcionario.getId(), inicio, fim);

        Tipo tipo = descobrirProximoPonto(registros);
        if (tipo == null) {
            throw new RuntimeException("Todas as batidas de hoje já foram realizadas");
        }


        PontoEletronico pontoEletronico = new PontoEletronico();
        pontoEletronico.setFuncionario(funcionario);
        pontoEletronico.setDataHora(LocalDateTime.now());
        pontoEletronico.setTipo(tipo);

        if (Tipo.SAIDA_FINAL.isSaida()) {
            long minutosPassados = totalHorasTrabalhadas(registros, pontoEletronico);
            pontoEletronico.setHoras_trabalhadas(minutosPassados);
        }

        pontoEletronicoRepository.save(pontoEletronico);

        return new PontoEletronicoDTO(
                "Ponto registrado com sucesso",
                funcionario.getCpf(),
                funcionario.getNome(),
                pontoEletronico.getDataHora(),
                pontoEletronico.getHoras_trabalhadas(),
                pontoEletronico.getTipo()
        );
    }


    private Tipo descobrirProximoPonto(List<PontoEletronico> registros) {
        if (registros.isEmpty()) {
            return Tipo.ENTRADA1;
        }

        PontoEletronico ultimoRegistro = registros.getLast();
        Tipo ultimo = ultimoRegistro.getTipo();
        LocalDateTime ultimaDataHora = ultimoRegistro.getDataHora();

        return switch (ultimo) {
            case ENTRADA1 -> Tipo.SAIDA;

            case SAIDA -> Tipo.ENTRADA2;

            case ENTRADA2 -> Tipo.SAIDA_FINAL;

            case SAIDA_FINAL -> {
                if (RESET_MESMO_DIA) {
                    yield Tipo.ENTRADA1;
                } else {
                    yield null;
                }
            }
        };

    }


    public long totalHorasTrabalhadas(List<PontoEletronico> reguistros, PontoEletronico pontoEletronico){
        long minutes = 0;

        reguistros.add(pontoEletronico);

        for (int i = 0; i < reguistros.size() - 1; i++){
            Tipo atual = reguistros.get(i).getTipo();
            Tipo proximo = reguistros.get(i + 1).getTipo();

            if (atual.isEntrada() && proximo.isSaida()) {
                LocalDateTime entrada = reguistros.get(i).getDataHora();
                LocalDateTime saida = reguistros.get(i + 1).getDataHora();
                minutes += Duration.between(entrada, saida).toHours();
            }
        }
        return minutes;

    }
}
