package com.projetoLocadora.locadora.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCliente;

    private int numInscricao;

    private Date dtNascimento;

    private Character sexo;

    private Boolean estahAtivo;

}
