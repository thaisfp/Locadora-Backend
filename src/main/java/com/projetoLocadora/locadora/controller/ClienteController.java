package com.projetoLocadora.locadora.controller;
import javax.management.relation.RelationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projetoLocadora.locadora.model.Cliente;
import com.projetoLocadora.locadora.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteService service;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra um novo cliente.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o cliente seja incluída com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> salvarcliente(@RequestBody Cliente novocliente) {
        try {
            return ResponseEntity.ok(service.saveAll(novocliente)) ;

        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
       
    }
    
    @GetMapping("/listar")
    @Operation(description = "Listagem dos clientes.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso os clientes sejam listados com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> listar(){

        try {
            return ResponseEntity.ok(service.listAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar clientes: " + e.getMessage());
        }
    }

    @GetMapping("/listar/{id}")
    @Operation(description = "Dado o id, um cliente é listado.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o cliente seja listado com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> obterIdcliente(@PathVariable Long id) throws RelationNotFoundException{
        try {
            return ResponseEntity.ok(service.listId(id));
         } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar clientes: " + e.getMessage());
        }
    }
    
    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, edita um cliente.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o cliente seja editado com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> editarcliente(@PathVariable Long id, @RequestBody Cliente cliente) throws RelationNotFoundException{
        try {
            return ResponseEntity.ok(service.editId(cliente, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(description = "Dado o nome, o cliente é deletado.", responses = {
        @ApiResponse(responseCode = "200", description = "Caso o cliente seja deletado com sucesso."),
        @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
        @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<String> deletarcliente(@PathVariable Long id){
        try{
            service.deleteId(id);
            return ResponseEntity.ok("cliente deletado");
        }catch(RelationNotFoundException erro){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }
}
