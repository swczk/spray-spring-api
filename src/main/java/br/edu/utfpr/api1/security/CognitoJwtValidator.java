package br.edu.utfpr.api1.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

@Service
public class CognitoJwtValidator {

   private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;
   private final String userPoolId;

   public CognitoJwtValidator(
         @Value("${aws.cognito.jwk.url}") String jwkUrl,
         @Value("${aws.cognito.userPoolId}") String userPoolId) throws MalformedURLException {
      this.userPoolId = userPoolId;

      // Create a JWT processor for the access tokens
      this.jwtProcessor = new DefaultJWTProcessor<>();

      // Configure the JWT processor with a remote JWK source
      JWKSource<SecurityContext> jwkSource = new RemoteJWKSet<>(new URL(jwkUrl));
      JWSAlgorithm algorithm = JWSAlgorithm.RS256; // Cognito uses RS256
      JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(algorithm, jwkSource);
      jwtProcessor.setJWSKeySelector(keySelector);
   }

   public JWTClaimsSet validateToken(String token)
         throws ParseException, BadJOSEException, JOSEException {
      // Process the token
      JWTClaimsSet claimsSet = jwtProcessor.process(token, null);

      // Verify the token is issued for our user pool
      String iss = claimsSet.getIssuer();
      if (!iss.equals("https://cognito-idp." +
            userPoolId.split("_")[0] + ".amazonaws.com/" + userPoolId)) {
         throw new JOSEException("Invalid issuer");
      }

      return claimsSet;
   }
}
