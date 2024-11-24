package com.projetoLocadora.locadora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Entity
public class Dependente extends Cliente{
    
    @JoinColumn(name = "idCliente")
    private Socio socio;
}
