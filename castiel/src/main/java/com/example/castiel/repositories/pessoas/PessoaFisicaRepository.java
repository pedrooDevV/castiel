package com.example.castiel.repositories;


import com.example.castiel.DTOs.PessoaFisicaDTO;
import com.example.castiel.entities.AdmEntities.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
    boolean existsByCpf(String cpf);

    Long findIdfByCpf(String cpf);
}
