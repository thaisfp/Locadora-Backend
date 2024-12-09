package com.projetoLocadora.locadora.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projetoLocadora.locadora.model.Cliente;
import com.projetoLocadora.locadora.model.Item;
import com.projetoLocadora.locadora.model.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, UUID> {

    @Query(value = "select * from locacao where dt_devolucao_efetiva is null", nativeQuery = true)
    List<Locacao> findAllLocacaoNaoDevolvida();

    @Query(value = "select * from locacao where dt_devolucao_efetiva is not null ", nativeQuery = true)
    List<Locacao> findAllLocacaoDevolvida();

    List<Locacao> findByCliente(Cliente cliente);
    List<Locacao> findByItem(Item item);

}