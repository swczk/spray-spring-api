package br.edu.utfpr.api1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AplicacaoDTO {
      @NotBlank(message = "Campo 'talhaoId' é obrigatório")
      private String talhaoId;

      @NotBlank(message = "Campo 'equipamentoId' é obrigatório")
      private String equipamentoId;

      @NotBlank(message = "Campo 'tipoAplicacaoId' é obrigatório")
      private String tipoAplicacaoId;

      @NotBlank(message = "Campo 'dataInicio' é obrigatório")
      private String dataInicio;

      @NotBlank(message = "Campo 'dataFim' é obrigatório")
      private String dataFim;

      @NotBlank(message = "Campo 'dosagem' é obrigatório")
      private String dosagem;

      @NotBlank(message = "Campo 'volumeAplicado' é obrigatório")
      private String volumeAplicado;

      @NotBlank(message = "Campo 'operador' é obrigatório")
      private String operador;

      @NotBlank(message = "Campo 'condicaoClimatica' é obrigatório")
      private String condicaoClimatica;

      @NotBlank(message = "Campo 'observacoes' é obrigatório")
      private String observacoes;

      @NotBlank(message = "Campo 'finalizada' é obrigatório")
      private Boolean finalizada;
}
