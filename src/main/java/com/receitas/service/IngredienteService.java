package com.receitas.service;

import com.receitas.dto.IngredienteRecord;
import com.receitas.entity.Ingrediente;
import com.receitas.entity.Unidade;
import com.receitas.repository.IngredienteRepository;
import com.receitas.repository.UnidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final UnidadeRepository unidadeRepository;

    public List<IngredienteRecord> listarIngredientes(String nome){
        return ingredienteRepository.listarIngredientes(nome);
    }

    public IngredienteRecord saveIngrediente(IngredienteRecord ingredienteRecord){
        Ingrediente ingrediente = Ingrediente.builder()
                .id(ingredienteRecord.id())
                .nome(ingredienteRecord.nome())
                .quantidade(ingredienteRecord.quantidade())
                .unidade(unidadeRepository.findByUnidade(ingredienteRecord.unidade()))
                .build();

        ingrediente = ingredienteRepository.save(ingrediente);

        return new IngredienteRecord(ingrediente.getId(), ingrediente.getNome(), ingrediente.getQuantidade(), ingrediente.getUnidade().getUnidade());
    }

    public IngredienteRecord getById(Long id){
        Ingrediente ingrediente = ingredienteRepository.findById(id).orElse(null);
        if(ingrediente == null) throw new EntityNotFoundException("Não foi possível encontrar o Ingrediente");

        return new IngredienteRecord(ingrediente.getId(), ingrediente.getNome(), ingrediente.getQuantidade(), ingrediente.getUnidade().getUnidade());
    }

    public List<String> listarUnidades(){
        return unidadeRepository.findAll().stream().map(Unidade::getUnidade).toList();
    }
}
