package com.example.castiel.entities.RhEntities;

import com.example.castiel.Enums.Tipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ponto_eletronico")
public class PontoEletronico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dt_trabalhada")
    private LocalDateTime dataHora;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();

    }

}
