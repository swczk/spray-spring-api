package br.edu.utfpr.springapi.dto;

import br.edu.utfpr.springapi.model.Usuario.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
   @NotBlank(message = "Campo 'token' é obrigatório")
   private String token;

   @NotBlank(message = "Campo 'nome' é obrigatório")
   private String nome;

   @NotBlank(message = "Campo 'email' é obrigatório")
   private String email;

   @NotBlank(message = "Campo 'senha' é obrigatório")
   private String senha;

   @NotBlank(message = "Campo 'role' é obrigatório")
   private Role role;

   @NotBlank(message = "Campo 'ativo' é obrigatório")
   private Boolean ativo;
}
