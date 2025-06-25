package br.edu.utfpr.springapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioStatusDto {
    @NotNull(message = "Campo 'ativo' é obrigatório")
    private Boolean ativo;
}
