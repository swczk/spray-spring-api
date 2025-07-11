package br.edu.utfpr.springapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
   @NotBlank(message = "Username é obrigatório")
   private String username;

   @NotBlank(message = "Password é obrigatório")
   private String password;
}
