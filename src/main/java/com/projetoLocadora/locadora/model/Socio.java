package com.projetoLocadora.locadora.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Socio extends Cliente{
    
    private String cpf;

    private String endereco;

    private String tel;
}
