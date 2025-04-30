package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.Talhao;
import br.edu.utfpr.api1.repository.TalhaoRepository;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/talhoes", produces = "application/json")
public class TalhaoController {
    private final TalhaoRepository talhaoRepository;

    TalhaoController(TalhaoRepository talhaoRepository) {
        this.talhaoRepository = talhaoRepository;
    }

    @GetMapping({ "", "/" })
    public Page<Talhao> getAll(Pageable pageable) {
        return talhaoRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talhao> getById(@PathVariable UUID id) {
        return talhaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping({ "", "/" })
    @ResponseStatus(HttpStatus.CREATED)
    public Talhao create(@Valid @RequestBody Talhao talhao) {
        return talhaoRepository.save(talhao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talhao> update(@PathVariable UUID id, @Valid @RequestBody Talhao talhao) {
        if (!talhaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        talhao.setId(id);
        return ResponseEntity.ok(talhaoRepository.save(talhao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!talhaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        talhaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    public Page<Talhao> getByNome(@PathVariable String nome, Pageable pageable) {
        return talhaoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @GetMapping("/cultura/{cultura}")
    public Page<Talhao> getByCultura(@PathVariable String cultura, Pageable pageable) {
        return talhaoRepository.findByCultura(cultura, pageable);
    }
}
