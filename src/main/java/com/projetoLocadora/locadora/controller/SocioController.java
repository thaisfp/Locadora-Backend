package com.projetoLocadora.locadora.controller;

import java.util.List;

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

import com.projetoLocadora.locadora.model.Socio;
import com.projetoLocadora.locadora.service.SocioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/socio")
@AllArgsConstructor
public class SocioController {

    @Autowired
    private final SocioService socioService;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra um novo sócio.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja incluído com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Socio salvarSocio(@RequestBody Socio novoSocio) {
        return socioService.saveSocio(novoSocio);

    }

    @PutMapping("/editar")
    @Operation(description = "Dado o nome, o sócio é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Socio editarSocio(@RequestBody Socio grava) throws RelationTypeNotFoundException {
        return socioService.editSocio(grava);
    }

    @PutMapping("/ativar")
    @Operation(description = "Dado o nome, o sócio é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Socio editarSocioDesativo(@RequestBody Socio grava) throws RelationTypeNotFoundException {
        return socioService.activeSocio(grava);
    }

    @PutMapping("/desativar")
    @Operation(description = "Dado o nome, o sócio é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Socio editarSocioAtivo(@RequestBody Socio grava) throws RelationTypeNotFoundException {
        return socioService.desactiveSocio(grava);
    }

     @GetMapping("/listar")
    @Operation(description = "Lista todos os socios.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso os socios sejam listadas com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<?> listarSocios() throws RelationNotFoundException {
        try {
            return ResponseEntity.ok(socioService.listAll());
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }

    @GetMapping("/listar/ativo")
    @Operation(description = "Retorna todos os sócios ativos cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Socio> listarSocioAtivos() {
        return socioService.listAllSociosAtivos();
    }

    @GetMapping("/listar/ativosmulta")
    @Operation(description = "Retorna todos os sócios ativos  e sem multa cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Socio> listarSocioAtivosESemMulta() {
        return socioService.listAllSociosAtivoseSemMulta();
    }

    @GetMapping("/listar/inativo")
    @Operation(description = "Retorna todos os sócios inativos cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public List<Socio> listarSocioInativos() {
        return socioService.listAllSociosInativos();
    }

    @GetMapping("/listar/{id}")
    @Operation(description = "Retorna o sócio cadastrado por id.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio ID seja listado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public Socio pegarIdSocio(@PathVariable Long id) throws RelationTypeNotFoundException {
        return socioService.listIdSocio(id);

    }

    @DeleteMapping("/deletar/{id}")
    @Operation(description = "Dado o id, deleta o sócio.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o sócio seja deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<String> deletarSocio(@PathVariable Long id) {
        try {
            socioService.deleteSocio(id);
            return ResponseEntity.ok("Sócio deletado com sucesso");
        } catch (RelationTypeNotFoundException erro) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + erro.getMessage());
        }
    }
}