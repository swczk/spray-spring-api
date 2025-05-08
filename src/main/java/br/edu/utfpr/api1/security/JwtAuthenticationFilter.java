package br.edu.utfpr.api1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
   private final JwtTokenProvider jwtTokenProvider;

   public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
      this.jwtTokenProvider = jwtTokenProvider;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request,
         HttpServletResponse response,
         FilterChain filterChain)
         throws ServletException, IOException {

      try {
         String jwt = getJwtFromRequest(request);

         if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            String username = jwtTokenProvider.extractUsername(jwt);
            Map<String, Object> claims = jwtTokenProvider.parseToken(jwt);

            // Set the authentication in the security context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  username,
                  null,
                  new ArrayList<>());

            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
      } catch (Exception e) {
         logger.error("Could not set user authentication in security context", e);
      }

      filterChain.doFilter(request, response);
   }

   private String getJwtFromRequest(HttpServletRequest request) {
      String bearerToken = request.getHeader("Authorization");

      if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }

      return null;
   }
}
