package com.projetoLocadora.locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoLocadora.locadora.model.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente, Long> {

    // @Query(value = "SELECT c.* FROM cliente c INNER JOIN dependente d ON c.num_inscricao = d.num_inscricao WHERE not exists (SELECT c.* FROM socio_dependentes sd WHERE c.estah_ativo = true and d.num_inscricao = sd.dependentes) and c.estah_ativo = true", nativeQuery = true)
    // List<Dependente> findByDependentesOption();

    // @Query(value = "SELECT c.* FROM cliente c INNER JOIN dependente d ON c.num_inscricao = d.num_inscricao WHERE c.estah_ativo = true", nativeQuery = true)
    // List<Dependente> findAllByDependentesAtivos();

    // @Query(value = "SELECT c.* FROM cliente c INNER JOIN dependente d ON c.num_inscricao = d.num_inscricao WHERE c.estah_ativo = false", nativeQuery = true)
    // List<Dependente> findAllByDependentesInativos();

    // @Query(value = "select c.*,d.* from cliente c, dependente d where d.num_inscricao = c.num_inscricao and c.num_inscricao not in (select c.num_inscricao from cliente c inner join locacao l on c.num_inscricao = l.cliente where  c.estah_ativo = true and(l.dt_devolucao_efetiva <= l.dt_devolucao_prevista or l.dt_devolucao_efetiva is null )) ", nativeQuery = true)
    // List<Dependente> findAllByDependentesativosesemmulta();

}