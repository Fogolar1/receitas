package com.receitas.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "ingredientes_receitas")
@IdClass(IngredientesReceitasPK.class)
public class IngredienteReceitas {

    @Id
    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;

    @Id
    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;

    private BigDecimal quantidade;
}
