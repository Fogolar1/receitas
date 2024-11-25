package com.receitas.dto;

import java.math.BigDecimal;

public record IngredienteRecord(
        Long id,
        String nome,
        BigDecimal quantidade,
        Long unidade
) {
}
