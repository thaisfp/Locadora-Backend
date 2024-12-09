package com.projetoLocadora.locadora.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projetoLocadora.locadora.model.Titulo;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, UUID> {

    @Query("SELECT t FROM Titulo t JOIN t.atores a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nomeator, '%'))")
    List<Titulo> findByAtores(String nomeator);

    @Query("SELECT t FROM Titulo t WHERE LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Titulo> findByNome(String nome);

    // @Query(value = "SELECT distinct t.* FROM titulo t where t.categoria like LOWER(CONCAT('%', :categoria, '%'))", nativeQuery = true)
    List<Titulo> findByCategoria(String categoria);
}
