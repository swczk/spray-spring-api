package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.Equipamento;
import br.edu.utfpr.api1.repository.EquipamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/equipamentos", produces = "application/json")
public class EquipamentoController {
    private final EquipamentoRepository equipamentoRepository;

    EquipamentoController(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @GetMapping({"", "/"})
    public Page<Equipamento> getAll(Pageable pageable) {
        return equipamentoRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> getById(@PathVariable UUID id) {
        return equipamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento create(@RequestBody Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> update(@PathVariable UUID id, @RequestBody Equipamento equipamento) {
        if (!equipamentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        equipamento.setId(id);
        return ResponseEntity.ok(equipamentoRepository.save(equipamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!equipamentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        equipamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    public Page<Equipamento> getByNome(@PathVariable String nome, Pageable pageable) {
        return equipamentoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @GetMapping("/fabricante/{fabricante}")
    public Page<Equipamento> getByFabricante(@PathVariable String fabricante, Pageable pageable) {
        return equipamentoRepository.findByFabricante(fabricante, pageable);
    }

    @GetMapping("/modelo/{modelo}")
    public Page<Equipamento> getByModelo(@PathVariable String modelo, Pageable pageable) {
        return equipamentoRepository.findByModelo(modelo, pageable);
    }
}
