package com.example.castiel.repositories;

import com.example.castiel.entities.RhEntities.PontoEletronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PontoEletronicoRepository extends JpaRepository<PontoEletronico, Long> {

    @Query("""
                SELECT p
                FROM PontoEletronico p
                WHERE p.funcionario.id = :id
                  AND p.dataHora BETWEEN :inicio AND :fim
                ORDER BY p.dataHora
           \s""")
    List<PontoEletronico> buscarBatidasDoDia(
            @Param("id") Long funcionarioId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
