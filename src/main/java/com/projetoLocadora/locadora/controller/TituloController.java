package com.projetoLocadora.locadora.controller;

import java.util.List;
import java.util.UUID;

import javax.management.relation.RelationNotFoundException;
import javax.management.relation.RelationTypeNotFoundException;

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

import com.projetoLocadora.locadora.model.Titulo;
import com.projetoLocadora.locadora.service.TituloService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/titulo")
public class TituloController {

    @Autowired
    private TituloService service;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra uma novo Titulo.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo seja incluída com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> salvarTitulo(@RequestBody Titulo novoTitulo) {
        try {
            return ResponseEntity.ok(service.saveAll(novoTitulo));
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
    public ResponseEntity<?> obterIdTitulo() throws RelationNotFoundException {
        try {
            return ResponseEntity.ok(service.listAll());
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }

    @GetMapping("/listar/{id}")
    @Operation(description = "Dado o id, lista um Titulo.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo seja listada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> obterIdTitulo(@PathVariable UUID id) throws RelationNotFoundException {
        try {
            return ResponseEntity.ok(service.listId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar Titulo: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, edita um Titulo.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo seja editada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> editarTitulo(@PathVariable UUID id, @RequestBody Titulo titulo)
            throws RelationNotFoundException {
        try {
            return ResponseEntity.ok(service.editId(titulo, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o Titulo: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(description = "Dado o nome, cadastra uma novo Titulo.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo seja deletada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> deletarTitulo(@PathVariable UUID id) {
        try {
            service.deletarId(id);
            return ResponseEntity.ok("Titulo deletado");
        } catch (RelationNotFoundException erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }

    @GetMapping("/listar/tituloNome/{nome}")
    @Operation(description = "Retorna o titulo cadastrado por nome do titulo.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o titulo seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Titulo> listarTituloNome(@PathVariable String nome) throws RelationTypeNotFoundException {
        return service.listTituloNome(nome);

    }

    @GetMapping("/listar/tituloAtor/{ator}")
    @Operation(description = "Retorna o titulo cadastrado por nome do ator.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o titulo seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Titulo> listarTituloAtor(@PathVariable String ator) throws RelationTypeNotFoundException {
        System.out.println("NOME ATOR: "+ ator);
        return service.listTituloAtor(ator);

    }

    @GetMapping("/listar/tituloCategoria/{categoria}")
    @Operation(description = "Retorna o titulo cadastrado por categoria.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o titulo seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Titulo> listarTitulocategoria(@PathVariable String categoria) throws RelationTypeNotFoundException {
        return service.listTituloCategoria(categoria);

    }
}
