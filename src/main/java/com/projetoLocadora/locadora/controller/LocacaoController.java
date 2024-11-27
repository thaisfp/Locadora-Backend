package com.projetoLocadora.locadora.controller;

import java.util.List;
import java.util.UUID;

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

import com.projetoLocadora.locadora.model.Locacao;
import com.projetoLocadora.locadora.service.LocacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/locacao")
@AllArgsConstructor
public class LocacaoController {

    @Autowired
    private final LocacaoService locacaoServ;

    @PostMapping("/criar")
    @Operation(description = "Dado o item, cadastra uma nova locacao.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a locacao seja efetuada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Locacao salvarLocacao(@RequestBody Locacao grava) {
        return locacaoServ.saveAll(grava);

    }

    @PutMapping("/editar")
    @Operation(description = "Dado o item, a locacao é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a locacao seja editada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Locacao editarLocacao(@RequestBody Locacao grava) throws RelationTypeNotFoundException {
        return locacaoServ.editAll(grava);

    }

    @GetMapping("/listar/pendente")
    @Operation(description = "Retorna todos as locações cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a locacão seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Locacao> listarLocacaoPendente() {
        return locacaoServ.listAllNaoDevolvido();
    }

    @GetMapping("/listar/devolvida")
    @Operation(description = "Retorna todos as locações cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a locacão seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Locacao> listarLocacaoDevolvida() {
        return locacaoServ.listAllDevolvido();
    }

    @GetMapping("/listar/{id}")
    @Operation(description = "Retorna a Locacao cadastrada por id.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a locacão ID seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Locacao pegarIdLocacao(@PathVariable UUID id) throws RelationTypeNotFoundException {
        return locacaoServ.listId(id);

    }

    @DeleteMapping("/deletar/{id}")
    @Operation(description = "Dado o id, deleta a locação.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a locação seja deletada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<String> deletarLocacao(@PathVariable UUID id) {
        try {
            locacaoServ.deleteId(id);
            return ResponseEntity.ok("Locacao deletado com sucesso");
        } catch (RelationTypeNotFoundException erro) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }
}