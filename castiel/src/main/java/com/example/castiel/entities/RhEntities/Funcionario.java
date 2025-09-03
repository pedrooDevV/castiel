package com.example.castiel.entities.RhEntities;

import com.example.castiel.Enums.StatusFuncionario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @NotBlank
    @Column(name = "cargo")
    private String cargo;

    @NotNull
    @Column(name = "dt_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "dt_demissao")
    private LocalDate dataDemissao;

    @Column(name = "salario")
    private BigDecimal salario;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusFuncionario status;

    @OneToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private PessoaFisica pessoaFisica;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<PontoEletronico> registros;

}
