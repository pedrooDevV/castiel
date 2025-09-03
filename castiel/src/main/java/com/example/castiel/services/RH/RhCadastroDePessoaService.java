package com.example.castiel.services.RH;


import com.example.castiel.DTOs.FuncionarioDTO;
import com.example.castiel.DTOs.PessoaFisicaDTO;
import com.example.castiel.DTOs.PessoaJuridicaDTO;
import com.example.castiel.Enums.StatusFuncionario;
import com.example.castiel.Exceptions.UsuarioNaoEncontradoException;
import com.example.castiel.entities.RhEntities.Funcionario;
import com.example.castiel.entities.RhEntities.PessoaFisica;
import com.example.castiel.entities.RhEntities.PessoaJuridica;
import com.example.castiel.entities.AuthErpEntities.Usuario;
import com.example.castiel.repositories.pessoas.FuncionarioRepository;
import com.example.castiel.repositories.UserRepository;
import com.example.castiel.repositories.pessoas.PessoaFisicaRepository;
import com.example.castiel.repositories.pessoas.PessoaJuridicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class RhCadastroDePessoaService {

    private static final Logger logger = LoggerFactory.getLogger(RhCadastroDePessoaService.class);

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    public void cadastrarPessoaFisica(PessoaFisicaDTO pessoaFisicaDTO) {

        Usuario usuario = userRepository.findByCpf(pessoaFisicaDTO.cpf())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuario não encontrado para vincular em pessoa fisica"
                ));

        if (pessoaFisicaRepository.existsByCpf(
                pessoaFisicaDTO.cpf())) {
            logger.warn("Cpf já existem na base: CPF={}", pessoaFisicaDTO.cpf());
        }

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome(pessoaFisicaDTO.nome());
        pessoaFisica.setCpf(pessoaFisicaDTO.cpf());
        pessoaFisica.setRg(pessoaFisicaDTO.pessoaRg());
        pessoaFisica.setNacionalidade(pessoaFisicaDTO.nacionalidade());
        pessoaFisica.setEmail(pessoaFisicaDTO.email());
        pessoaFisica.setData_nascimento(pessoaFisicaDTO.data_nascimento());
        pessoaFisica.setUsuario(usuario);

        PessoaFisica pessoaSalva = pessoaFisicaRepository.save(pessoaFisica);

        logger.info(
                "Pessoa Fisica salva com sucesso: NOME={} CPF={} RG={} NACIONALIDADE={} EMAIL={} NASCIMENTO={}",
                pessoaFisicaDTO.nome(),
                pessoaFisicaDTO.cpf(),
                pessoaFisicaDTO.pessoaRg(),
                pessoaFisicaDTO.nacionalidade(),
                pessoaFisicaDTO.email(),
                pessoaFisicaDTO.data_nascimento()
        );

    }

    public void cadastrarPessoaJuridica(PessoaJuridicaDTO pessoaJuridicaDTO) {

        if (pessoaJuridicaRepository.existsByCnpj(
                pessoaJuridicaDTO.cnpj())) {
            logger.warn("Cnpj já existe na base: CNPJ={}", pessoaJuridicaDTO.cnpj());
        }

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj(pessoaJuridicaDTO.cnpj());
        pessoaJuridica.setRazao_social(pessoaJuridicaDTO.razao_social());
        pessoaJuridica.setNome_fantasia(pessoaJuridicaDTO.nome_fantasia());

        pessoaJuridicaRepository.save(pessoaJuridica);
        logger.info("Pessoa jurídica salva com sucesso: CNPJ={} RAZÃO_SOCIAL={} NOME_FANTASIA={}",
                pessoaJuridicaDTO.cnpj() , pessoaJuridicaDTO.razao_social(), pessoaJuridicaDTO.nome_fantasia());

    }

    //Esse metodo serve tanto para cadastrar como para atualizar o funcionario.
    public void cadastrarFuncionario(FuncionarioDTO funcionarioDTO){

        PessoaFisica pessoa = pessoaFisicaRepository.findByCpf(funcionarioDTO.cpf())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com CPF: " + funcionarioDTO.cpf()));

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDTO.nome());
        funcionario.setCpf(funcionarioDTO.cpf());
        funcionario.setPessoaFisica(pessoa);
        funcionario.setCargo(funcionarioDTO.cargo());
        funcionario.setDataAdmissao(LocalDate.now());
        funcionario.setDataDemissao(funcionarioDTO.dataDemissao());
        funcionario.setSalario(BigDecimal.valueOf(funcionarioDTO.salario()));

        if (funcionario.getDataAdmissao() != null){
            funcionario.setStatus(StatusFuncionario.ATIVO);
        } else {
            funcionario.setStatus(StatusFuncionario.DESATIVADO);
        }

        funcionarioRepository.save(funcionario);
        logger.info("Funcionario criado: nome={}, cpf={}, cargo={}, salario={}",
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getCargo(),
                funcionario.getSalario());


    }

    public void atualizarFuncionario(Long id, FuncionarioDTO funcionarioDTO) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

        PessoaFisica pessoa = funcionario.getPessoaFisica();

        if (funcionarioDTO.cargo() != null) funcionario.setCargo(funcionarioDTO.cargo());

        if (funcionarioDTO.dataDemissao() != null) {
            funcionario.setDataDemissao(funcionarioDTO.dataDemissao());
            funcionario.setStatus(StatusFuncionario.DESATIVADO);
        }

        if (funcionarioDTO.nome() != null) pessoa.setNome(funcionarioDTO.nome());
        if (funcionarioDTO.cpf() != null) pessoa.setCpf(funcionarioDTO.cpf());



        pessoaFisicaRepository.save(pessoa);
        funcionarioRepository.save(funcionario);

        logger.info("Funcionario atualizado: nome={}, cpf={}, cargo={}",
                pessoa.getNome(), pessoa.getCpf(), funcionario.getCargo());
    }

}
