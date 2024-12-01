package com.projetoLocadora.locadora.model;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)    
    private Long numInscricao;

    private String nome;
    
    private Date dtNascimento;

    private Character sexo;

    private boolean estahAtivo;

}
