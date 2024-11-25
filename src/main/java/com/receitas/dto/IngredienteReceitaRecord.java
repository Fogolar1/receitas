package com.receitas.dto;

import java.math.BigDecimal;

public record IngredienteReceitaRecord(
        Long ingredienteId,
        BigDecimal quantidade
) {
}
