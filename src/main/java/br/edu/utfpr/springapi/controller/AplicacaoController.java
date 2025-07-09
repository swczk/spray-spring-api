package br.edu.utfpr.springapi.controller;

import br.edu.utfpr.springapi.dto.AplicacaoStatusDTO;
import br.edu.utfpr.springapi.enums.EventType;
import br.edu.utfpr.springapi.mapper.AplicacaoMapper;
import br.edu.utfpr.springapi.model.Aplicacao;
import br.edu.utfpr.springapi.repository.AplicacaoRepository;
import br.edu.utfpr.springapi.repository.EquipamentoRepository;
import br.edu.utfpr.springapi.repository.TalhaoRepository;
import br.edu.utfpr.springapi.repository.TipoAplicacaoRepository;
import br.edu.utfpr.springapi.service.MessageProducerService;
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
    private final MessageProducerService messageProducerService;
    private final AplicacaoMapper aplicacaoMapper;

    AplicacaoController(
            AplicacaoRepository aplicacaoRepository,
            TalhaoRepository talhaoRepository,
            EquipamentoRepository equipamentoRepository,
            TipoAplicacaoRepository tipoAplicacaoRepository,
            MessageProducerService messageProducerService,
            AplicacaoMapper aplicacaoMapper) {
        this.aplicacaoRepository = aplicacaoRepository;
        this.talhaoRepository = talhaoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.tipoAplicacaoRepository = tipoAplicacaoRepository;
        this.messageProducerService = messageProducerService;
        this.aplicacaoMapper = aplicacaoMapper;
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

        Aplicacao savedAplicacao = aplicacaoRepository.save(aplicacao);
        
        // Enviar evento de criação
        messageProducerService.sendApplicationEvent(
            EventType.APPLICATION_CREATED,
            aplicacaoMapper.toDTO(savedAplicacao),
            "system" // TODO: Pegar do contexto de autenticação
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAplicacao);
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
        Aplicacao updatedAplicacao = aplicacaoRepository.save(aplicacao);
        
        // Enviar evento de atualização
        messageProducerService.sendApplicationEvent(
            EventType.APPLICATION_UPDATED,
            aplicacaoMapper.toDTO(updatedAplicacao),
            "system" // TODO: Pegar do contexto de autenticação
        );
        
        return ResponseEntity.ok(updatedAplicacao);
    }

    @Operation(summary = "Deletar aplicação", description = "Deleta uma aplicação existente pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return aplicacaoRepository.findById(id)
                .map(aplicacao -> {
                    aplicacaoRepository.deleteById(id);
                    
                    // Enviar evento de exclusão
                    messageProducerService.sendApplicationEvent(
                        EventType.APPLICATION_DELETED,
                        aplicacaoMapper.toDTO(aplicacao),
                        "system" // TODO: Pegar do contexto de autenticação
                    );
                    
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
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
                    boolean statusChanged = !aplicacao.getFinalizada().equals(statusDto.getFinalizada());
                    
                    aplicacao.setFinalizada(statusDto.getFinalizada());

                    if (statusDto.getFinalizada()) {
                        aplicacao.setDataFim(LocalDateTime.now());
                    }

                    Aplicacao updatedAplicacao = aplicacaoRepository.save(aplicacao);
                    
                    // Enviar evento baseado no status
                    if (statusChanged) {
                        EventType eventType = statusDto.getFinalizada() ? 
                            EventType.APPLICATION_COMPLETED : 
                            EventType.APPLICATION_STATUS_UPDATED;
                            
                        messageProducerService.sendApplicationEvent(
                            eventType,
                            aplicacaoMapper.toDTO(updatedAplicacao),
                            "system" // TODO: Pegar do contexto de autenticação
                        );
                    }
                    
                    return ResponseEntity.ok(updatedAplicacao);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
