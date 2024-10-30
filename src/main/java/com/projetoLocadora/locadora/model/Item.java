package com.projetoLocadora.locadora.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int numSerie;

    private Date dtAquisicao;

    private String tipoItem;
    
    @ManyToOne
    @JoinColumn(name = "idTitulo")
    private Titulo titulo;
    
}