package com.projetoLocadora.locadora.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Titulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTitulo;

    private String nome;

    private int ano;

    private String sinopse;

    private String categoria;

    @ManyToOne
    private Classe classe;

    @ManyToOne
    private Diretor diretor;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable ( name = "titulo_ator", joinColumns = @JoinColumn( name = "idTitulo"), inverseJoinColumns = @JoinColumn (name = "idAtores"))
    private List<Ator> atores;

}
