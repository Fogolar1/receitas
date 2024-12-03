package com.receitas.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class IngredientesReceitasPK implements Serializable {

    private Long ingrediente;

    private Long receita;


}
