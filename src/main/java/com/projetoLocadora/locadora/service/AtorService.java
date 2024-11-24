package com.projetoLocadora.locadora.service;

import java.util.UUID;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoLocadora.locadora.model.Ator;
import com.projetoLocadora.locadora.repository.AtorRepository;

@Service
public class AtorService {
    
    @Autowired
    private AtorRepository repository;

    public Ator saveAll(Ator ator){
        repository.save(ator);
        return ator;
    }

    public Iterable<Ator> listAll(){
        return repository.findAll();
    }

    public Ator listId (UUID id) throws RelationNotFoundException{
        return repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe ator com ID: " + id));
    }

    public void deleteId(UUID id) throws RelationNotFoundException{
        Ator deletada = repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe ator com ID: " + id));
        
        repository.delete(deletada);
    }

    public Ator editId(Ator ator, UUID id) throws RelationNotFoundException{
        Ator alterado = repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe ator com ID: " + id));

        alterado.setNome(ator.getNome());

        return repository.save(alterado);
    }
}
