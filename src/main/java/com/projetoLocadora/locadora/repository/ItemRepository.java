package com.projetoLocadora.locadora.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoLocadora.locadora.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    
}
