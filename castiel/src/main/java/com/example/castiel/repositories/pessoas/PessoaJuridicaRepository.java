package com.example.castiel.repositories.pessoas;

import com.example.castiel.entities.RhEntities.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
    boolean existsByCnpj(String cnpj);
}
