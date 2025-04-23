package br.edu.utfpr.api1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
   private final JwtUtil jwtUtil;

   public JwtFilter(JwtUtil jwtUtil) {
      this.jwtUtil = jwtUtil;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {
      String authHeader = request.getHeader("Authorization");

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
         String token = authHeader.substring(7);

         try {
            String userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));

            List<SimpleGrantedAuthority> authorities = Collections
                  .singletonList(new SimpleGrantedAuthority("ROLE_" + role));

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                  userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
         } catch (Exception e) {
            // Token inválido, não autenticar
         }
      }

      filterChain.doFilter(request, response);
   }
}
