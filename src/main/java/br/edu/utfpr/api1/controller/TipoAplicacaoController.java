package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.TipoAplicacao;
import br.edu.utfpr.api1.repository.TipoAplicacaoRepository;
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
@Tag(name = "Tipo Aplicação", description = "endpoints de tipos de aplicação")
@RequestMapping(value = "/tipos-aplicacao", produces = "application/json")
public class TipoAplicacaoController {
    private final TipoAplicacaoRepository tipoAplicacaoRepository;

    TipoAplicacaoController(TipoAplicacaoRepository tipoAplicacaoRepository) {
        this.tipoAplicacaoRepository = tipoAplicacaoRepository;
    }

    @Operation(summary = "Listar tipos de aplicação", description = "Retorna uma lista paginada de tipos de aplicação.")
    @GetMapping({ "", "/" })
    public Page<TipoAplicacao> getAll(Pageable pageable) {
        return tipoAplicacaoRepository.findAll(pageable);
    }

    @Operation(summary = "Buscar tipo de aplicação por ID", description = "Retorna um tipo de aplicação específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TipoAplicacao> getById(@PathVariable UUID id) {
        return tipoAplicacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar tipo de aplicação", description = "Cria um novo tipo de aplicação.")
    @PostMapping({ "", "/" })
    @ResponseStatus(HttpStatus.CREATED)
    public TipoAplicacao create(@Valid @RequestBody TipoAplicacao tipoAplicacao) {
        return tipoAplicacaoRepository.save(tipoAplicacao);
    }

    @Operation(summary = "Atualizar tipo de aplicação", description = "Atualiza um tipo de aplicação existente.")
    @PutMapping("/{id}")
    public ResponseEntity<TipoAplicacao> update(@PathVariable UUID id,
            @Valid @RequestBody TipoAplicacao tipoAplicacao) {
        if (!tipoAplicacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoAplicacao.setId(id);
        return ResponseEntity.ok(tipoAplicacaoRepository.save(tipoAplicacao));
    }

    @Operation(summary = "Deletar tipo de aplicação", description = "Deleta um tipo de aplicação existente.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!tipoAplicacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoAplicacaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar tipo de aplicação por nome", description = "Retorna uma lista paginada de tipos de aplicação pelo nome.")
    @GetMapping("/nome/{nome}")
    public Page<TipoAplicacao> getByNome(@PathVariable String nome, Pageable pageable) {
        return tipoAplicacaoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @Operation(summary = "Buscar tipo de aplicação por tipo de produto", description = "Retorna uma lista paginada de tipos de aplicação pelo tipo de produto.")
    @GetMapping("/tipo-produto/{tipoProduto}")
    public Page<TipoAplicacao> getByTipoProduto(@PathVariable String tipoProduto, Pageable pageable) {
        return tipoAplicacaoRepository.findByTipoProduto(tipoProduto, pageable);
    }
}
