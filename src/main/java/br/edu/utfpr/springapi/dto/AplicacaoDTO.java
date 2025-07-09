package br.edu.utfpr.springapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AplicacaoDTO {
      private UUID id;
      
      @NotBlank(message = "Campo 'talhaoId' é obrigatório")
      private String talhaoId;

      @NotBlank(message = "Campo 'equipamentoId' é obrigatório")
      private String equipamentoId;

      @NotBlank(message = "Campo 'tipoAplicacaoId' é obrigatório")
      private String tipoAplicacaoId;

      private LocalDateTime dataInicio;
      private LocalDateTime dataFim;

      @NotBlank(message = "Campo 'dosagem' é obrigatório")
      private String dosagem;

      @NotBlank(message = "Campo 'volumeAplicado' é obrigatório")
      private String volumeAplicado;

      @NotBlank(message = "Campo 'operador' é obrigatório")
      private String operador;

      @NotBlank(message = "Campo 'condicaoClimatica' é obrigatório")
      private String condicaoClimatica;

      private String observacoes;
      private Boolean finalizada;
}
