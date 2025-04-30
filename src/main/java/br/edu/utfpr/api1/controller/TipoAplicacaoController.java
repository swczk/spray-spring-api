package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.TipoAplicacao;
import br.edu.utfpr.api1.repository.TipoAplicacaoRepository;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/tipos-aplicacao", produces = "application/json")
public class TipoAplicacaoController {
    private final TipoAplicacaoRepository tipoAplicacaoRepository;

    TipoAplicacaoController(TipoAplicacaoRepository tipoAplicacaoRepository) {
        this.tipoAplicacaoRepository = tipoAplicacaoRepository;
    }

    @GetMapping({ "", "/" })
    public Page<TipoAplicacao> getAll(Pageable pageable) {
        return tipoAplicacaoRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAplicacao> getById(@PathVariable UUID id) {
        return tipoAplicacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping({ "", "/" })
    @ResponseStatus(HttpStatus.CREATED)
    public TipoAplicacao create(@Valid @RequestBody TipoAplicacao tipoAplicacao) {
        return tipoAplicacaoRepository.save(tipoAplicacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoAplicacao> update(@PathVariable UUID id,
            @Valid @RequestBody TipoAplicacao tipoAplicacao) {
        if (!tipoAplicacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoAplicacao.setId(id);
        return ResponseEntity.ok(tipoAplicacaoRepository.save(tipoAplicacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!tipoAplicacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoAplicacaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    public Page<TipoAplicacao> getByNome(@PathVariable String nome, Pageable pageable) {
        return tipoAplicacaoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @GetMapping("/tipo-produto/{tipoProduto}")
    public Page<TipoAplicacao> getByTipoProduto(@PathVariable String tipoProduto, Pageable pageable) {
        return tipoAplicacaoRepository.findByTipoProduto(tipoProduto, pageable);
    }
}
