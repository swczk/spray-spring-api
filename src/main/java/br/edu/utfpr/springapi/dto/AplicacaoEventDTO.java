package br.edu.utfpr.springapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AplicacaoEventDTO {
    private String eventType;
    private LocalDateTime timestamp;
    private AplicacaoDTO aplicacao;
    private String userId;
    private String eventDescription;
}