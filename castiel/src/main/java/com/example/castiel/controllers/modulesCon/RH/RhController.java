package com.example.castiel.controllers.modulesCon.RH;

import com.example.castiel.DTOs.FuncionarioDTO;
import com.example.castiel.DTOs.PessoaFisicaDTO;
import com.example.castiel.DTOs.PessoaJuridicaDTO;
import com.example.castiel.DTOs.PontoEletronicoDTO;
import com.example.castiel.services.RH.RhCadastroDePessoaService;
import com.example.castiel.services.RH.RhPontoEletronicoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rh")
public class RhController {

    @Autowired
    private RhPontoEletronicoService rhPontoEletronicoService;

    @Autowired
    private RhCadastroDePessoaService rhCadastroDePessoaService;

    @PostMapping("/cadastro/pessoa-fisica")
    public ResponseEntity<Map<String, String>> cadastro_pessoa_fisica(@RequestBody PessoaFisicaDTO pessoaFisicaDTO) {
        try {
            rhCadastroDePessoaService.cadastrarPessoaFisica(pessoaFisicaDTO);
            log.info("Cadastro realizado com sucesso para CPF={}", pessoaFisicaDTO.cpf());
            return ResponseEntity.status(201).body(Map.of("message", "Pessoa cadastrada com sucesso"));
        } catch (RuntimeException e) {
            log.error("Erro ao salvar pessoa física para CPF={}: {}", pessoaFisicaDTO.cpf(), e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("Error", "Erro ao salvar pessoa física: " + e.getMessage()));
        }
    }

    @PostMapping("/cadastro/pessoa-juridica")
    public ResponseEntity<Map<String, String>> cadastro_pessoa_juridica(@RequestBody PessoaJuridicaDTO pessoaJuridicaDTO) {
        try {
            rhCadastroDePessoaService.cadastrarPessoaJuridica(pessoaJuridicaDTO);
            log.info("Cadastro realizado com sucesso para CNPJ={}", pessoaJuridicaDTO.cnpj());
            return ResponseEntity.status(201).body(Map.of("message", "Pessoa jurídica cadastrada com sucesso"));
        } catch (RuntimeException e) {
            log.error("Erro ao salvar pessoa jurídica para ?=CNPJ{} : {}", pessoaJuridicaDTO.cnpj(), e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("Error", "Erro ao salvar pessoa jurídica: " + e.getMessage()));
        }
    }

    @PostMapping("/cadastro/funcionario")
    public ResponseEntity<Map<String, String>> cadastroFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            rhCadastroDePessoaService.cadastrarFuncionario(funcionarioDTO);
            log.info("Cadastro de funcionário realizado com sucesso. CPF={}, Nome={}",
                    funcionarioDTO.cpf(), funcionarioDTO.nome());
            return ResponseEntity.status(201).body(Map.of(
                    "message", "Funcionário cadastrado com sucesso"
            ));
        } catch (RuntimeException e) {
            log.error("Erro ao salvar funcionário. CPF={}, Erro={}",
                    funcionarioDTO.cpf(), e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Erro ao salvar funcionário: " + e.getMessage()
            ));
        }
    }

    @PutMapping("/atualizar/funcionario/{id}")
    public ResponseEntity<Map<String, String>> atualizarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO, @PathVariable Long id) {
        try {
            rhCadastroDePessoaService.atualizarFuncionario(id, funcionarioDTO);
            log.info("Atualização de funcionário realizado com sucesso. CPF={}, Nome={}",
                    funcionarioDTO.cpf(), funcionarioDTO.nome());
            return ResponseEntity.status(201).body(Map.of("message", "Funcionário atualizado com sucesso"));

        } catch (RuntimeException e) {
            log.error("Não foi possivel atualizar o funcionário CPF={} Erro={}",
                    funcionarioDTO.cpf(), e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("error", " Erro ao atualizar funcionario " + e.getMessage()
            ));
        }
    }

    @PostMapping("/registrar-ponto")
    public ResponseEntity<PontoEletronicoDTO> registra_ponto(@RequestBody PontoEletronicoDTO pontoEletronicoDTO) {
        try {
            PontoEletronicoDTO response = rhPontoEletronicoService.RegistrarPonto(pontoEletronicoDTO);
            return ResponseEntity.status(201).body(response);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar ponto para CPF={}: {}", pontoEletronicoDTO.cpf(), e.getMessage(), e);
            return ResponseEntity.status(500).body(
                    new PontoEletronicoDTO("Erro ao salvar ponto: " + e.getMessage(), pontoEletronicoDTO.cpf(), null, null, pontoEletronicoDTO.horasTrabalhadas(), null)
            );
        }
    }

}


