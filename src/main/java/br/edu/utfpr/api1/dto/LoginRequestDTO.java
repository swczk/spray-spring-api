package br.edu.utfpr.api1.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
   private String email;
   private String senha;
}
