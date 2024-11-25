package com.receitas.repository;

import com.receitas.entity.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query("SELECT r FROM Receita r WHERE (:nome IS NULL or upper(:nome) = upper(r.nome))")
    public List<Receita> findByNome(String nome);
}
