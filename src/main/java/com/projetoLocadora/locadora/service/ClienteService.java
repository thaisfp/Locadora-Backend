package com.projetoLocadora.locadora.service;

import java.util.List;
import java.util.UUID;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoLocadora.locadora.model.Cliente;
import com.projetoLocadora.locadora.repository.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repository;

    public Cliente saveAll(Cliente Cliente){
        repository.save(Cliente);
        return Cliente;
    }

    public List<Cliente> listAll(){
        return repository.findAll();
    }

    public Cliente listId (Long id) throws RelationNotFoundException{
        return repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe Cliente com ID: " + id));
    }

    public void deleteId(Long id) throws RelationNotFoundException{
        Cliente deletada = repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe Cliente com ID: " + id));
        
        repository.delete(deletada);
    }

    public Cliente editId(Cliente Cliente, Long id) throws RelationNotFoundException{
        Cliente alterado = repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe Cliente com ID: " + id));

        alterado.setNome(Cliente.getNome());
        alterado.setNumInscricao(Cliente.getNumInscricao());
        alterado.setSexo(Cliente.getSexo());
        alterado.setEstahAtivo(Cliente.isEstahAtivo());
        alterado.setDtNascimento(Cliente.getDtNascimento());

        return repository.save(alterado);
    }
}
