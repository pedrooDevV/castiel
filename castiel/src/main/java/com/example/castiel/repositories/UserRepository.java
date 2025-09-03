package com.example.castiel.repositories;

import com.example.castiel.entities.RhEntities.PessoaFisica;
import com.example.castiel.entities.AuthErpEntities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario>findByEmail(String email);

    Optional<Usuario> findByNome(String nome);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByPessoaFisica(PessoaFisica pessoaFisica);
}
