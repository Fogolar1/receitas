package com.receitas.controller;

import com.receitas.dto.ReceitaGridRecord;
import com.receitas.dto.ReceitaRecord;
import com.receitas.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receita")
public class ReceitaController {

    private final ReceitaService receitaService;

    @PostMapping("/save")
    public ReceitaRecord save(@RequestBody ReceitaRecord receitaRecord) {
        return receitaService.saveReceita(receitaRecord);
    }

    @GetMapping("/list")
    public List<ReceitaGridRecord> list(@RequestParam String nome) {
        return receitaService.listarReceitas(nome);
    }

    @GetMapping("/{id}")
    public ReceitaRecord findById(@PathVariable Long id) {
        return receitaService.findReceitaById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        receitaService.deleteReceitaById(id);
    }
}
