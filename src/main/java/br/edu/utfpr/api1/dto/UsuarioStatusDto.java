package br.edu.utfpr.api1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioStatusDto {
    @NotBlank(message = "Campo 'ativo' é obrigatório")
    private Boolean ativo;
}
