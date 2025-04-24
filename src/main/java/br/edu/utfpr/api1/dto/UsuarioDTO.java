package br.edu.utfpr.api1.dto;

import org.springframework.beans.BeanUtils;

import br.edu.utfpr.api1.model.Usuario;
import br.edu.utfpr.api1.model.Usuario.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
   private String token;
   private String nome;
   private String email;
   private String senha;
   private Role role;
   private Boolean ativo;
}


