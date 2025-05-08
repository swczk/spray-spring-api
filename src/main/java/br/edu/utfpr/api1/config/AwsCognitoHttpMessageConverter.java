package br.edu.utfpr.api1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class AwsCognitoHttpMessageConverter extends AbstractJackson2HttpMessageConverter {
   public AwsCognitoHttpMessageConverter(ObjectMapper objectMapper) {
      super(objectMapper, MediaType.parseMediaType("application/x-amz-json-1.1"));
   }
}
