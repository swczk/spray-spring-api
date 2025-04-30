package br.edu.utfpr.api1.dto;

import jakarta.validation.constraints.NotBlank;

public class TipoAplicacaoDTO {
    @NotBlank(message = "Campo 'nome' é obrigatório")
    private String nome;

    @NotBlank(message = "Campo 'descricao' é obrigatório")
    private String descricao;

    @NotBlank(message = "Campo 'vazaoPadrao' é obrigatório")
    private Double vazaoPadrao;

    @NotBlank(message = "Campo 'tipoProduto' é obrigatório")
    private String tipoProduto;

    @NotBlank(message = "Campo 'unidadeMedida' é obrigatório")
    private String unidadeMedida;
}
