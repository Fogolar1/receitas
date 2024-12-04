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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final IngredientesReceitasRepository ingredientesReceitasRepository;
    private final IngredienteRepository ingredienteRepository;

    public ReceitaRecord saveReceita(ReceitaRecord receitaRecord){
        Receita receita = receitaRepository.findById(receitaRecord.id() == null ? 0 : receitaRecord.id()).orElse(Receita.builder().build());
        receita.setNome(receitaRecord.nome());
        receita.setProcedimento(receitaRecord.procedimento());

        receita = receitaRepository.save(receita);

        if(!CollectionUtils.isEmpty(receita.getIngredienteReceitas())){
            ingredientesReceitasRepository.deleteAll(receita.getIngredienteReceitas());
            receita.setIngredienteReceitas(new HashSet<>());
        }

        for(IngredienteReceitaRecord ingredienteReceita : receitaRecord.ingredientes()){
            Ingrediente ingrediente = ingredienteRepository.findById(ingredienteReceita.ingredienteId()).orElse(null);
            if(ingrediente == null) throw new EntityNotFoundException("Ingrediente não encontrado");

            IngredienteReceitas ingredienteReceitas = IngredienteReceitas.builder()
                    .ingrediente(ingrediente)
                    .receita(receita)
                    .quantidade(ingredienteReceita.quantidade())
                    .build();

            receita.addIngredienteReceita(ingredienteReceitas);

            ingredientesReceitasRepository.save(ingredienteReceitas);
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

    public void fazerReceita(Long id){
        Receita receita = receitaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Receita não encontrada"));

        Set<IngredienteReceitas> ingredientesReceitas = receita.getIngredienteReceitas();
        if(CollectionUtils.isEmpty(ingredientesReceitas)) throw new RuntimeException("Receita sem ingredientes");

        for(IngredienteReceitas ingredienteReceita : ingredientesReceitas){
            Ingrediente ingrediente = ingredienteReceita.getIngrediente();
            BigDecimal quantidadeReceita = ingredienteReceita.getQuantidade();
            if(ingrediente.getQuantidade().subtract(quantidadeReceita).compareTo(BigDecimal.ZERO) < 0) throw new RuntimeException("Ingrediente insuficiente");
            ingrediente.setQuantidade(ingrediente.getQuantidade().subtract(quantidadeReceita));
            ingredienteRepository.save(ingrediente);
        }
    }
}
