package com.receitas.dto;

import java.util.List;

public record ReceitaRecord(
        Long id,
        String nome,
        String procedimento,
        List<IngredienteReceitaRecord> ingredientes
)
{}
