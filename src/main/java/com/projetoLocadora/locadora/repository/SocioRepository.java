package com.projetoLocadora.locadora.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projetoLocadora.locadora.model.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    @Query(value = "SELECT c.*, s.* FROM cliente c INNER JOIN socio s ON c.num_inscricao = s.num_inscricao WHERE c.estah_ativo = true", nativeQuery = true)
    List<Socio> findAllBySocioAtivo();

    @Query(value = "SELECT c.*, s.* FROM cliente c INNER JOIN socio s ON c.num_inscricao = s.num_inscricao WHERE c.estah_ativo = false", nativeQuery = true)
    List<Socio> findAllBySocioInativo();

    @Query(value = "select c.*,s.* from cliente c, socio s where s.num_inscricao = c.num_inscricao and c.num_inscricao not in (select c.num_inscricao from cliente c inner join locacao l on c.num_inscricao = l.cliente where  c.estah_ativo = true and(l.dt_devolucao_efetiva <= l.dt_devolucao_prevista or l.dt_devolucao_efetiva is null )) ", nativeQuery = true)
    List<Socio> findAllBySocioAtivoeSemMulta();

    Optional<Socio> findByNome(String nome);
    boolean existsByNome(String nome);

}