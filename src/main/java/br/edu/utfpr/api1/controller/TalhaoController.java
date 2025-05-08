package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.Talhao;
import br.edu.utfpr.api1.repository.TalhaoRepository;
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
@Tag(name = "Talhão", description = "endpoints de talhões")
@RequestMapping(value = "/talhoes", produces = "application/json")
public class TalhaoController {
    private final TalhaoRepository talhaoRepository;

    TalhaoController(TalhaoRepository talhaoRepository) {
        this.talhaoRepository = talhaoRepository;
    }

    @Operation(summary = "Listar talhões", description = "Retorna uma lista paginada de talhões.")
    @GetMapping({ "", "/" })
    public Page<Talhao> getAll(Pageable pageable) {
        return talhaoRepository.findAll(pageable);
    }

    @Operation(summary = "Buscar talhão por ID", description = "Retorna um talhão específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Talhao> getById(@PathVariable UUID id) {
        return talhaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Criar talhão", description = "Cria um novo talhão.")
    @PostMapping({ "", "/" })
    @ResponseStatus(HttpStatus.CREATED)
    public Talhao create(@Valid @RequestBody Talhao talhao) {
        return talhaoRepository.save(talhao);
    }

    @Operation(summary = "Atualizar talhão", description = "Atualiza um talhão existente pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Talhao> update(@PathVariable UUID id, @Valid @RequestBody Talhao talhao) {
        if (!talhaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        talhao.setId(id);
        return ResponseEntity.ok(talhaoRepository.save(talhao));
    }

    @Operation(summary = "Deletar talhão", description = "Deleta um talhão existente pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!talhaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        talhaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar talhão por nome", description = "Retorna uma lista paginada de talhões filtrados pelo nome.")
    @GetMapping("/nome/{nome}")
    public Page<Talhao> getByNome(@PathVariable String nome, Pageable pageable) {
        return talhaoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @Operation(summary = "Buscar talhão por cultura", description = "Retorna uma lista paginada de talhões filtrados pela cultura.")
    @GetMapping("/cultura/{cultura}")
    public Page<Talhao> getByCultura(@PathVariable String cultura, Pageable pageable) {
        return talhaoRepository.findByCultura(cultura, pageable);
    }
}
