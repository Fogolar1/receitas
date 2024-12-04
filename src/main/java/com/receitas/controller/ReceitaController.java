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
@CrossOrigin(origins = "http://localhost:3000")
public class ReceitaController {

    private final ReceitaService receitaService;

    @PostMapping("/save")
    public ReceitaRecord save(@RequestBody ReceitaRecord receitaRecord) {
        return receitaService.saveReceita(receitaRecord);
    }

    @GetMapping("/list")
    public List<ReceitaGridRecord> list(@RequestParam(required = false) String nome) {
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

    @PostMapping("/fazer/{id}")
    public void fazerReceita(@PathVariable Long id) {
        receitaService.fazerReceita(id);
    }
}
