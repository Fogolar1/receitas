package com.receitas.repository;

import com.receitas.dto.IngredienteRecord;
import com.receitas.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    @Query("SELECT new com.receitas.dto.IngredienteRecord(id, nome, quantidade, unidade.id) FROM Ingrediente WHERE (:nome IS NULL OR upper(nome) LIKE upper(:nome)) ORDER BY quantidade DESC")
    List<IngredienteRecord> listarIngredientes(String nome);
}
