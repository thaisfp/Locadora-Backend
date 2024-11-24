package com.projetoLocadora.locadora.controller;
import java.util.UUID;

import javax.management.relation.RelationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoLocadora.locadora.model.Ator;
import com.projetoLocadora.locadora.service.AtorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/ator")
public class AtorController {
    
    @Autowired
    private AtorService service;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra um novo ator.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o ator seja incluída com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> salvarAtor(@RequestBody Ator novoAtor) {
        try {
            return ResponseEntity.ok(service.saveAll(novoAtor)) ;

        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
       
    }
    
    @GetMapping("/listar")
    @Operation(description = "Listagem dos atores.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso os atores sejam listados com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> listar(){

        try {
            return ResponseEntity.ok(service.listAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar atores: " + e.getMessage());
        }
    }

    @GetMapping("/listar/{id}")
    @Operation(description = "Dado o id, um ator é listado.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o ator seja listado com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> obterIdAtor(@PathVariable UUID id) throws RelationNotFoundException{
        try {
            return ResponseEntity.ok(service.listId(id));
         } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar atores: " + e.getMessage());
        }
    }
    
    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, edita um ator.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o ator seja editado com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> editarAtor(@PathVariable UUID id, @RequestBody Ator ator) throws RelationNotFoundException{
        try {
            return ResponseEntity.ok(service.editId(ator, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(description = "Dado o nome, o ator é deletado.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o ator seja deletado com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<String> deletarAtor(@PathVariable UUID id){
        try{
            service.deleteId(id);
            return ResponseEntity.ok("Ator deletado");
        }catch(RelationNotFoundException erro){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }
}
