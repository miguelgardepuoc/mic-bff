package com.antharos.bff.boot.config;

import com.antharos.bff.infrastructure.config.security.jwt.JwtTokenInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final CorsProperties corsProperties;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins(this.corsProperties.getAllowedOrigins().toArray(new String[0]))
        .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
        .allowedHeaders("*");
  }

  @Autowired private JwtTokenInterceptor jwtTokenInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtTokenInterceptor);
  }
}
