package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.Equipamento;
import br.edu.utfpr.api1.repository.EquipamentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Equipamento", description = "endpoints de equipamentos")
@RequestMapping(value = "/equipamentos", produces = "application/json")
public class EquipamentoController {
    private final EquipamentoRepository equipamentoRepository;

    EquipamentoController(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @Operation(summary = "Listar equipamentos", description = "Retorna uma lista paginada de equipamentos.")
    @GetMapping({ "", "/" })
    public Page<Equipamento> getAll(Pageable pageable) {
        return equipamentoRepository.findAll(pageable);
    }

    @Operation(summary = "Buscar equipamento por ID", description = "Retorna um equipamento específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> getById(@PathVariable UUID id) {
        return equipamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar equipamento", description = "Cria um novo equipamento.")
    @PostMapping({ "", "/" })
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento create(@Valid @RequestBody Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    @Operation(summary = "Atualizar equipamento", description = "Atualiza um equipamento existente pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> update(@PathVariable UUID id, @Valid @RequestBody Equipamento equipamento) {
        if (!equipamentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        equipamento.setId(id);
        return ResponseEntity.ok(equipamentoRepository.save(equipamento));
    }

    @Operation(summary = "Deletar equipamento", description = "Deleta um equipamento existente pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!equipamentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        equipamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar equipamentos por nome", description = "Retorna uma lista paginada de equipamentos pelo nome.")
    @GetMapping("/nome/{nome}")
    public Page<Equipamento> getByNome(@PathVariable String nome, Pageable pageable) {
        return equipamentoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @Operation(summary = "Buscar equipamentos por fabricante", description = "Retorna uma lista paginada de equipamentos pelo fabricante.")
    @GetMapping("/fabricante/{fabricante}")
    public Page<Equipamento> getByFabricante(@PathVariable String fabricante, Pageable pageable) {
        return equipamentoRepository.findByFabricante(fabricante, pageable);
    }

    @Operation(summary = "Buscar equipamentos por modelo", description = "Retorna uma lista paginada de equipamentos pelo modelo.")
    @GetMapping("/modelo/{modelo}")
    public Page<Equipamento> getByModelo(@PathVariable String modelo, Pageable pageable) {
        return equipamentoRepository.findByModelo(modelo, pageable);
    }
}
