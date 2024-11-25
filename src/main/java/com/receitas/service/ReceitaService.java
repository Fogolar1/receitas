package com.receitas.service;

import com.receitas.dto.IngredienteReceitaRecord;
import com.receitas.dto.ReceitaRecord;
import com.receitas.entity.Ingrediente;
import com.receitas.entity.IngredienteReceitas;
import com.receitas.entity.Receita;
import com.receitas.repository.IngredienteRepository;
import com.receitas.repository.IngredientesReceitasRepository;
import com.receitas.repository.ReceitaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final IngredientesReceitasRepository ingredientesReceitasRepository;
    private final IngredienteRepository ingredienteRepository;


    public ReceitaRecord saveReceita(ReceitaRecord receitaRecord){
        Receita receita = Receita.builder()
                .id(receitaRecord.id())
                .nome(receitaRecord.nome())
                .procedimento(receitaRecord.procedimento())
                .build();

        receita = receitaRepository.save(receita);

        if(receitaRecord.id() != null) ingredientesReceitasRepository.deleteAll(receita.getIngredienteReceitas());

        for(IngredienteReceitaRecord ingredienteReceita : receitaRecord.ingredientes()){
            Ingrediente ingrediente = ingredienteRepository.findById(ingredienteReceita.ingredienteId()).orElse(null);
            if(ingrediente == null) throw new EntityNotFoundException("Ingrediente não encontrado");

            IngredienteReceitas ingredienteReceitas = IngredienteReceitas.builder()
                    .ingrediente(ingrediente)
                    .receita(receita)
                    .quantidade(ingredienteReceita.quantidade())
                    .build();

            ingredientesReceitasRepository.save(ingredienteReceitas);

            receita.addIngredienteReceita(ingredienteReceitas);
        }

        receitaRepository.save(receita);

        return
    }

    public List<ReceitaRecord> listarReceitas(String nome){

    }
}
