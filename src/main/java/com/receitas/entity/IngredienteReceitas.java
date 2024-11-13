package com.receitas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ingredientes_receitas")
public class IngredienteReceitas {

    @EmbeddedId
    IngredientesReceitasPK id;

    @ManyToOne
    @MapsId("ingredienteId")
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;

    @ManyToOne
    @MapsId("receitaId")
    @JoinColumn(name = "receita_id")
    private Receita receita;

    private Long quantidade;
}
