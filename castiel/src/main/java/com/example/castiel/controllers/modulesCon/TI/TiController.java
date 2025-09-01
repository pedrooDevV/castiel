package com.example.castiel.controllers.modules.TI;

import com.example.castiel.DTOs.PessoaFisicaDTO;
import com.example.castiel.services.TI.TiCadastroDePessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ti")
public class TiController {

    @Autowired
    private TiCadastroDePessoaService tiCadastroDePessoaService;

    @PostMapping("/cadastro")
    public ResponseEntity<Map<String,String>> cadastro(@RequestBody PessoaFisicaDTO pessoaFisicaDTO) {
        try {
            tiCadastroDePessoaService.cadastrarPessoaFisica(pessoaFisicaDTO);
            log.info("Cadastro realizado com sucesso para CPF={}", pessoaFisicaDTO.cpf());
            return ResponseEntity.status(201).body(Map.of("message", "Pessoa registrada com sucesso"));
        } catch (RuntimeException e) {
            log.error("Erro ao salvar pessoa física para CPF={}: {}", pessoaFisicaDTO.cpf(), e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao salvar pessoa física: " + e.getMessage()));
        }
    }
}
