package br.edu.utfpr.api1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AplicacaoDTO {
      private String talhaoId;
      private String equipamentoId;
      private String tipoAplicacaoId;
      private String dataInicio;
      private String dataFim;
      private String dosagem;
      private String volumeAplicado;
      private String operador;
      private String condicaoClimatica;
      private String observacoes;
      private Boolean finalizada;
}
