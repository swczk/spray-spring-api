package br.edu.utfpr.api1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

   @Bean
   public RestTemplate restTemplate(RestTemplateBuilder builder) {
      RestTemplate restTemplate = builder.build();

      // Add custom converter for AWS Cognito
      ObjectMapper objectMapper = new ObjectMapper();
      restTemplate.getMessageConverters().add(new AwsCognitoHttpMessageConverter(objectMapper));

      return restTemplate;
   }
}
