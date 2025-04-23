package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.dto.LoginRequest;
import br.edu.utfpr.api1.dto.LoginResponse;
import br.edu.utfpr.api1.model.Usuario;
import br.edu.utfpr.api1.repository.UsuarioRepository;
import br.edu.utfpr.api1.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
   private final UsuarioRepository usuarioRepository;
   private final JwtUtil jwtUtil;

   public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
      this.usuarioRepository = usuarioRepository;
      this.jwtUtil = jwtUtil;
   }

   @PostMapping("/login")
   public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
      Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

      if (usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(loginRequest.getSenha())) {
         Usuario usuario = usuarioOpt.get();
         String token = jwtUtil.generateToken(usuario);
         return ResponseEntity.ok(new LoginResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getRole()));
      }

      return ResponseEntity.status(401).build();
   }
}
