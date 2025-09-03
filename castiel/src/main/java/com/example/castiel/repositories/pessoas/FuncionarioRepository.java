package com.example.castiel.repositories.pessoas;

import com.example.castiel.entities.RhEntities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {
}
