package br.edu.utfpr.api1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
   private String token; // Access token
   private String idToken; // ID token containing user info
   private String refreshToken;
   private Integer expiresIn;
}
