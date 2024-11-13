package com.receitas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class IngredientesReceitasPK implements Serializable {

    @Column(name = "ingrediente_id")
    private Long ingredienteId;

    @Column(name = "receita_id")
    private Long receitaId;


}
