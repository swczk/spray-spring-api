package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.dto.AplicacaoStatusDTO;
import br.edu.utfpr.api1.model.Aplicacao;
import br.edu.utfpr.api1.repository.AplicacaoRepository;
import br.edu.utfpr.api1.repository.EquipamentoRepository;
import br.edu.utfpr.api1.repository.TalhaoRepository;
import br.edu.utfpr.api1.repository.TipoAplicacaoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Aplicação", description = "endpoints de aplicações")
@RequestMapping(value = "/api/aplicacoes", produces = "application/json")
public class AplicacaoController {
    private final AplicacaoRepository aplicacaoRepository;
    private final TalhaoRepository talhaoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final TipoAplicacaoRepository tipoAplicacaoRepository;

    AplicacaoController(
            AplicacaoRepository aplicacaoRepository,
            TalhaoRepository talhaoRepository,
            EquipamentoRepository equipamentoRepository,
            TipoAplicacaoRepository tipoAplicacaoRepository) {
        this.aplicacaoRepository = aplicacaoRepository;
        this.talhaoRepository = talhaoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.tipoAplicacaoRepository = tipoAplicacaoRepository;
    }

    @Operation(summary = "Listar aplicações", description = "Retorna uma lista paginada de aplicações.")
    @GetMapping({ "", "/" })
    public Page<Aplicacao> getAll(Pageable pageable) {
        return aplicacaoRepository.findAll(pageable);
    }


    @Operation(summary = "Buscar aplicação por ID", description = "Retorna uma aplicação específica pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Aplicacao> getById(@PathVariable UUID id) {
        return aplicacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar aplicação", description = "Cria uma nova aplicação.")
    @PostMapping({ "", "/" })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Aplicacao> create(@Valid @RequestBody Aplicacao aplicacao) {
        // Verificar se os IDs das entidades relacionadas existem
        if (aplicacao.getTalhao() != null && aplicacao.getTalhao().getId() != null) {
            if (!talhaoRepository.existsById(aplicacao.getTalhao().getId())) {
                return ResponseEntity.badRequest().build();
            }
        }

        if (aplicacao.getEquipamento() != null && aplicacao.getEquipamento().getId() != null) {
            if (!equipamentoRepository.existsById(aplicacao.getEquipamento().getId())) {
                return ResponseEntity.badRequest().build();
            }
        }

        if (aplicacao.getTipoAplicacao() != null && aplicacao.getTipoAplicacao().getId() != null) {
            if (!tipoAplicacaoRepository.existsById(aplicacao.getTipoAplicacao().getId())) {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(aplicacaoRepository.save(aplicacao));
    }

    @Operation(summary = "Atualizar aplicação", description = "Atualiza uma aplicação existente pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Aplicacao> update(@PathVariable UUID id, @Valid @RequestBody Aplicacao aplicacao) {
        if (!aplicacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se os IDs das entidades relacionadas existem
        if (aplicacao.getTalhao() != null && aplicacao.getTalhao().getId() != null) {
            if (!talhaoRepository.existsById(aplicacao.getTalhao().getId())) {
                return ResponseEntity.badRequest().build();
            }
        }

        if (aplicacao.getEquipamento() != null && aplicacao.getEquipamento().getId() != null) {
            if (!equipamentoRepository.existsById(aplicacao.getEquipamento().getId())) {
                return ResponseEntity.badRequest().build();
            }
        }

        if (aplicacao.getTipoAplicacao() != null && aplicacao.getTipoAplicacao().getId() != null) {
            if (!tipoAplicacaoRepository.existsById(aplicacao.getTipoAplicacao().getId())) {
                return ResponseEntity.badRequest().build();
            }
        }

        aplicacao.setId(id);
        return ResponseEntity.ok(aplicacaoRepository.save(aplicacao));
    }

    @Operation(summary = "Deletar aplicação", description = "Deleta uma aplicação existente pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!aplicacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aplicacaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar aplicações por talhão", description = "Retorna uma lista paginada de aplicações filtradas pelo talhão.")
    @GetMapping("/talhao/{id}")
    public Page<Aplicacao> getByTalhao(@PathVariable UUID id, Pageable pageable) {
        return talhaoRepository.findById(id)
                .map(talhao -> aplicacaoRepository.findByTalhao(talhao, pageable))
                .orElse(Page.empty(pageable));
    }

    @Operation(summary = "Buscar aplicações por equipamento", description = "Retorna uma lista paginada de aplicações filtradas pelo equipamento.")
    @GetMapping("/equipamento/{id}")
    public Page<Aplicacao> getByEquipamento(@PathVariable UUID id, Pageable pageable) {
        return equipamentoRepository.findById(id)
                .map(equipamento -> aplicacaoRepository.findByEquipamento(equipamento, pageable))
                .orElse(Page.empty(pageable));
    }

    @Operation(summary = "Buscar aplicações por tipo de aplicação", description = "Retorna uma lista paginada de aplicações filtradas pelo tipo de aplicação.")
    @GetMapping("/tipo-aplicacao/{id}")
    public Page<Aplicacao> getByTipoAplicacao(@PathVariable UUID id, Pageable pageable) {
        return tipoAplicacaoRepository.findById(id)
                .map(tipoAplicacao -> aplicacaoRepository.findByTipoAplicacao(tipoAplicacao, pageable))
                .orElse(Page.empty(pageable));
    }

    @Operation(summary = "Buscar aplicações por status de finalização", description = "Retorna uma lista paginada de aplicações filtradas pelo status de finalização.")
    @GetMapping("/finalizada/{finalizada}")
    public Page<Aplicacao> getByFinalizada(@PathVariable Boolean finalizada, Pageable pageable) {
        return aplicacaoRepository.findByFinalizada(finalizada, pageable);
    }

    @Operation(summary = "Atualizar status da aplicação", description = "Atualiza o status de finalização de uma aplicação pelo ID.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Aplicacao> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody AplicacaoStatusDTO statusDto) {

        return aplicacaoRepository.findById(id)
                .map(aplicacao -> {
                    aplicacao.setFinalizada(statusDto.getFinalizada());

                    if (statusDto.getFinalizada()) {
                        aplicacao.setDataFim(LocalDateTime.now());
                    }

                    return ResponseEntity.ok(aplicacaoRepository.save(aplicacao));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
