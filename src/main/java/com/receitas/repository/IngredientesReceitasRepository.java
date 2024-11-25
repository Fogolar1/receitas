package com.receitas.repository;

import com.receitas.entity.IngredienteReceitas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientesReceitasRepository extends JpaRepository<IngredienteReceitas, Long> {
}
