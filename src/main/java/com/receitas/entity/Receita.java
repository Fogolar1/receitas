package com.receitas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "receitas")
public class Receita {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String procedimento;

    @OneToMany(mappedBy = "receita")
    private Set<IngredienteReceitas> ingredienteReceitas;

    public void addIngredienteReceita(IngredienteReceitas ingredienteReceitas){
        if(this.ingredienteReceitas == null) this.ingredienteReceitas = new HashSet<>();

        this.ingredienteReceitas.add(ingredienteReceitas);
    }
}
