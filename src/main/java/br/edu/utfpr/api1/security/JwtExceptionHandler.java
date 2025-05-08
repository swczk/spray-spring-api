package br.edu.utfpr.api1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionHandler implements AuthenticationEntryPoint {

   private final ObjectMapper objectMapper = new ObjectMapper();

   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response,
         AuthenticationException authException) throws IOException {

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      Map<String, Object> errorDetails = new HashMap<>();
      errorDetails.put("message", "Unauthorized access");
      errorDetails.put("error", "Invalid or expired token");

      objectMapper.writeValue(response.getOutputStream(), errorDetails);
   }
}
