package br.edu.utfpr.springapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

   @Autowired
   private CognitoTokenValidationInterceptor cognitoTokenValidationInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(cognitoTokenValidationInterceptor)
            .addPathPatterns("/api/**");
   }
}
