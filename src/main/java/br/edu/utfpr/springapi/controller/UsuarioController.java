package br.edu.utfpr.springapi.controller;

import br.edu.utfpr.springapi.dto.UsuarioStatusDto;
import br.edu.utfpr.springapi.model.Usuario;
import br.edu.utfpr.springapi.repository.UsuarioRepository;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuário", description = "endpoints de usuários")
@RequestMapping(value = "/api/usuarios", produces = "application/json")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Operation(summary = "Listar usuários",description = "Retorna uma lista paginada de usuários.")
    @GetMapping({ "", "/" })
    public Page<Usuario> getAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Operation(summary = "Buscar usuário por ID",description = "Retorna um usuário específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable UUID id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar usuário",description = "Cria um novo usuário.")
    @PostMapping({ "", "/" })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
        // Verificar se já existe um usuário com o mesmo email
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Todo: Adicionar criptografia da senha antes de salvar

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @Operation(summary = "Atualizar usuário",description = "Atualiza um usuário existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable UUID id, @Valid @RequestBody Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se o email atualizado já está em uso por outro usuário
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new RuntimeException("Email já está em uso");
                    }
                });

        // Todo: Adicionar criptografia da senha antes de salvar, se a senha for
        // alterada

        usuario.setId(id);
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @Operation(summary = "Deletar usuário",description = "Deleta um usuário pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar o status de um usuário",description = "Atualizar o status de um usuário (ativo/inativo) pelo ID.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Usuario> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UsuarioStatusDto statusDto) {

        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setAtivo(statusDto.getAtivo());
                    return ResponseEntity.ok(usuarioRepository.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
