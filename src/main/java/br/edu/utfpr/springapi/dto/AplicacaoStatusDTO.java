package br.edu.utfpr.springapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AplicacaoStatusDTO {
    @NotNull(message = "Campo 'finalizada' é obrigatório")
    private Boolean finalizada;
}
