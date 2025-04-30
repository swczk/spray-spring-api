package br.edu.utfpr.api1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AplicacaoStatusDTO {
    @NotBlank(message = "Campo 'finalizada' é obrigatório")
    private Boolean finalizada;
}
