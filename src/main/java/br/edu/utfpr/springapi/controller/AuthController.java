package br.edu.utfpr.springapi.controller;

import br.edu.utfpr.springapi.dto.AuthRequest;
import br.edu.utfpr.springapi.dto.AuthResponseDTO;
import br.edu.utfpr.springapi.security.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
   private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

   @Value("${aws.cognito.url}")
   private String cognitoUrl;

   @Value("${aws.cognito.clientId}")
   private String clientId;

   @Value("${aws.cognito.clientSecret}")
   private String clientSecret;

   private final RestTemplate restTemplate;
   private final JwtTokenProvider jwtTokenProvider;

   public AuthController(RestTemplate restTemplate, JwtTokenProvider jwtTokenProvider) {
      this.restTemplate = restTemplate;
      this.jwtTokenProvider = jwtTokenProvider;
   }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
      try {
         String secretHash = calculateSecretHash(authRequest.getUsername());

         // Set up auth parameters
         Map<String, Object> requestBody = new HashMap<>();
         requestBody.put("AuthFlow", "USER_PASSWORD_AUTH");
         requestBody.put("ClientId", clientId);

         Map<String, String> authParams = new HashMap<>();
         authParams.put("USERNAME", authRequest.getUsername());
         authParams.put("PASSWORD", authRequest.getPassword());
         authParams.put("SECRET_HASH", secretHash);
         requestBody.put("AuthParameters", authParams);

         // Configure headers exactly as required by AWS Cognito
         HttpHeaders headers = new HttpHeaders();
         headers.set("Content-Type", "application/x-amz-json-1.1");
         headers.set("X-Amz-Target", "AWSCognitoIdentityProviderService.InitiateAuth");

         HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

         logger.info("Sending request to Cognito: {}", requestBody);

         ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
               cognitoUrl,
               HttpMethod.POST,
               request,
               new ParameterizedTypeReference<Map<String, Object>>() {});

         logger.debug("Response from Cognito: {}", response.getBody());

         if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            @SuppressWarnings("unchecked")
            Map<String, Object> authResult = (Map<String, Object>) response.getBody().get("AuthenticationResult");

            if (authResult != null) {
               AuthResponseDTO authResponse = new AuthResponseDTO();
               authResponse.setToken((String) authResult.get("AccessToken"));
               authResponse.setIdToken((String) authResult.get("IdToken"));
               authResponse.setRefreshToken((String) authResult.get("RefreshToken"));
               authResponse.setExpiresIn((Integer) authResult.get("ExpiresIn"));

               return ResponseEntity.ok(authResponse);
            } else {
               logger.error("No AuthenticationResult in response: {}", response.getBody());
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                     .body(Map.of("message", "No authentication result returned"));
            }
         }

         return ResponseEntity.status(response.getStatusCode())
               .body(response.getBody());
      } catch (RestClientException e) {
         logger.error("Cognito API error", e);
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
               .body(Map.of("message", "Cognito authentication failed: " + e.getMessage()));
      } catch (Exception e) {
         logger.error("Login error", e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(Map.of("error", "Login failed: " + e.getMessage()));
      }
   }

   @PostMapping("/validate")
   public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
         String token = authHeader.substring(7);

         try {
            Map<String, Object> claims = jwtTokenProvider.parseToken(token);
            boolean isValid = jwtTokenProvider.validateToken(token);

            if (isValid) {
               return ResponseEntity.ok(Map.of(
                     "valid", true,
                     "claims", claims));
            }
         } catch (Exception e) {
            logger.error("Token validation error", e);
         }
      }

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("valid", false, "message", "Invalid token"));
   }

   @PostMapping("/refresh")
   public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> payload) {
      String refreshToken = payload.get("refreshToken");
      String username = payload.get("username");

      if (refreshToken == null || refreshToken.isEmpty() || username == null || username.isEmpty()) {
         return ResponseEntity.badRequest()
               .body(Map.of("error", "refresh_token and username required"));
      }

      try {
         // Extract the actual Cognito username from the token
         Map<String, Object> tokenClaims = jwtTokenProvider.parseToken(payload.get("idToken"));
         String cognitoUsername = (String) tokenClaims.getOrDefault("sub", username);

         // Calculate secret hash with the Cognito username (sub)
         String secretHash = calculateSecretHash(cognitoUsername);

         Map<String, String> authParams = new HashMap<>();
         authParams.put("REFRESH_TOKEN", refreshToken);
         authParams.put("SECRET_HASH", secretHash);

         Map<String, Object> requestBody = new HashMap<>();
         requestBody.put("AuthFlow", "REFRESH_TOKEN_AUTH");
         requestBody.put("ClientId", clientId);
         requestBody.put("AuthParameters", authParams);

         HttpHeaders headers = new HttpHeaders();
         headers.set("Content-Type", "application/x-amz-json-1.1");
         headers.set("X-Amz-Target", "AWSCognitoIdentityProviderService.InitiateAuth");

         HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

         // Log the request for debugging
         logger.info("Refresh token request: {}", requestBody);

         ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
               cognitoUrl,
               HttpMethod.POST,
               request,
               new ParameterizedTypeReference<Map<String, Object>>() {});

         if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            @SuppressWarnings("unchecked")
            Map<String, Object> authResult = (Map<String, Object>) response.getBody().get("AuthenticationResult");

            if (authResult != null) {
               AuthResponseDTO authResponse = new AuthResponseDTO();
               authResponse.setToken((String) authResult.get("AccessToken"));
               authResponse.setIdToken((String) authResult.get("IdToken"));
               authResponse.setRefreshToken(refreshToken); // Keep the existing refresh token
               authResponse.setExpiresIn((Integer) authResult.get("ExpiresIn"));

               return ResponseEntity.ok(authResponse);
            }
         }

         return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
               .body(Map.of("error", "Token refresh failed"));

      } catch (Exception e) {
         logger.error("Token refresh error", e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(Map.of("error", "Token refresh failed: " + e.getMessage()));
      }
   }

   private String calculateSecretHash(String username) throws Exception {
      String message = username + clientId;
      Mac mac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      mac.init(secretKey);
      byte[] hmacData = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(hmacData);
   }
}
