package br.edu.utfpr.api1.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioStatusDto {
    private Boolean ativo;
}
