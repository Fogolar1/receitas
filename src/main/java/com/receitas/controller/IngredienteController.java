package com.receitas.controller;

import com.receitas.dto.IngredienteRecord;
import com.receitas.service.IngredienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingrediente")
@CrossOrigin(origins = "http://localhost:3000")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @GetMapping("/list")
    public List<IngredienteRecord> listaIngredientes(@RequestParam(required = false) String nome){
        return ingredienteService.listarIngredientes(nome);
    }

    @PostMapping("/save")
    public IngredienteRecord saveIngrediente(@RequestBody IngredienteRecord ingredienteRecord){
        return ingredienteService.saveIngrediente(ingredienteRecord);
    }

    @GetMapping("/{id}")
    public IngredienteRecord getIngredienteById(@PathVariable Long id){
        return ingredienteService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredienteById(@PathVariable Long id){
        ingredienteService.deleteById(id);
    }

    @GetMapping("/unidades")
    public List<String> listarUnidades(){
        return ingredienteService.listarUnidades();
    }
}
