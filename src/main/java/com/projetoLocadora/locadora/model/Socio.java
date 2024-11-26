package com.projetoLocadora.locadora.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Socio extends Cliente{
    
    private String cpf;

    private String endereco;

    private String tel;

    @OneToMany
    @JoinColumn(name = "dependente")
    private List<Dependente> dependentes;
}
