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
public class Locacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idLocacao;

    private Date dtLocacao;

    private Date dtDevolucaoPrevista;

    private Date dtDevolucaoEfetiva;

    private Double valorCobrado;

    private Double multaCobrada;

    @ManyToOne
    @JoinColumn(name = "idItem")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
}
