package com.projetoLocadora.locadora.controller;

import java.util.UUID;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoLocadora.locadora.model.Item;
import com.projetoLocadora.locadora.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra uma novo Item.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item seja incluída com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> salvarItem(@RequestBody Item novoItem) {
        try {
            return ResponseEntity.ok(service.saveAll(novoItem));
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todos os itens.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso os itens sejam listadas com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> listarItem() throws RelationNotFoundException {
        try {
            return ResponseEntity.ok(service.listAll());
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }

    @GetMapping("/listar/{id}")
    @Operation(description = "Dado o id, lista um Item.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item seja listada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> obterIdItem(@PathVariable UUID id) throws RelationNotFoundException {
        try {
            return ResponseEntity.ok(service.listId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar Item: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, edita um Item.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item seja editada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> editarItem(@PathVariable UUID id, @RequestBody Item item)
            throws RelationNotFoundException {
        try {
            return ResponseEntity.ok(service.editId(item, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o Item: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(description = "Dado o nome, cadastra uma novo Item.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item seja deletada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> deletarItem(@PathVariable UUID id) {
        try {
            service.deletarId(id);
            return ResponseEntity.ok("Item deletado");
        } catch (RelationNotFoundException erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }
}
