package br.edu.utfpr.springapi.dto;

import jakarta.validation.constraints.NotBlank;

public class TalhaoDTO {
    @NotBlank(message = "Campo 'nome' é obrigatório")
    private String nome;

    @NotBlank(message = "Campo 'areaHectares' é obrigatório")
    private String areaHectares;

    @NotBlank(message = "Campo 'cultura' é obrigatório")
    private String cultura;

    @NotBlank(message = "Campo 'variedade' é obrigatório")
    private String variedade;

    @NotBlank(message = "Campo 'coordenadasGeograficas' é obrigatório")
    private String coordenadasGeograficas;
}
