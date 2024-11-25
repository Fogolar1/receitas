package com.receitas.service;

import com.receitas.dto.IngredienteReceitaRecord;
import com.receitas.dto.ReceitaGridRecord;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        return new ReceitaRecord(receita.getId(), receita.getNome(), receita.getProcedimento(), receitaRecord.ingredientes());
    }

    public List<ReceitaGridRecord> listarReceitas(String nome){
        List<Receita> receitas = receitaRepository.findByNome(nome);
        List<ReceitaGridRecord> receitaRecords = new ArrayList<>();

        for(Receita receita : receitas){
            boolean available = true;
            Set<IngredienteReceitas> ingredienteReceitas = receita.getIngredienteReceitas();
            for(IngredienteReceitas ingredienteReceita : ingredienteReceitas){
                BigDecimal quantidadeReceita = ingredienteReceita.getQuantidade();
                Ingrediente ingrediente = ingredienteReceita.getIngrediente();
                if (quantidadeReceita.compareTo(ingrediente.getQuantidade()) > 0) {
                    available = false;
                    break;
                }
            }
            receitaRecords.add(new ReceitaGridRecord(receita.getId(), receita.getNome(), available));
        }

        return receitaRecords;
    }

    public ReceitaRecord findReceitaById(Long id){
        Receita receita = receitaRepository.findById(id).orElse(null);

        if(receita == null)
            throw new EntityNotFoundException("Não foi possível encontrar a receita");

        List<IngredienteReceitaRecord> ingredientesReceitaList = new ArrayList<>();
        if(receita.getIngredienteReceitas() != null){
            for(IngredienteReceitas ingredienteReceita : receita.getIngredienteReceitas()){
                ingredientesReceitaList.add(new IngredienteReceitaRecord(ingredienteReceita.getIngrediente().getId(), ingredienteReceita.getQuantidade()));
            }
        }

        return new ReceitaRecord(receita.getId(), receita.getNome(), receita.getProcedimento(), ingredientesReceitaList);
    }

    public void deleteReceitaById(Long id){
        receitaRepository.deleteById(id);
    }
}
