package br.edu.utfpr.springapi.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public class EquipamentoDTO {
    @NotBlank(message = "Campo 'nome' é obrigatório")
    private String nome;

    @NotBlank(message = "Campo 'modelo' é obrigatório")
    private String modelo;

    @NotBlank(message = "Campo 'fabricante' é obrigatório")
    private String fabricante;

    @NotBlank(message = "Campo 'anoFabricacao' é obrigatório")
    private String anoFabricacao;

    @NotBlank(message = "Campo 'larguraBarra' é obrigatório")
    private String larguraBarra;

    @NotBlank(message = "Campo 'CapacidadeTanque' é obrigatório")
    private BigDecimal CapacidadeTanque;

    @NotBlank(message = "Campo 'NumeroSerie' é obrigatório")
    private String NumeroSerie;

    @NotBlank(message = "Campo 'ativo' é obrigatório")
    private Boolean ativo;
}
