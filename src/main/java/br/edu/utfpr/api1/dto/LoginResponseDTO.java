package br.edu.utfpr.api1.dto;

import br.edu.utfpr.api1.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
   private String token;
   private String nome;
   private String email;
   private Usuario.Role role;
}
