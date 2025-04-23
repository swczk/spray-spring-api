package br.edu.utfpr.api1.dto;

import lombok.Data;

@Data
public class LoginRequest {
   private String email;
   private String senha;
}
