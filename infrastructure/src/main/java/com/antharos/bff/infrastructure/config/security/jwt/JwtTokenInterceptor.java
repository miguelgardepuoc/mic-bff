package com.antharos.bff.infrastructure.config.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

  private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

  public static String getToken() {
    return tokenHolder.get();
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    tokenHolder.set(token);
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    tokenHolder.remove();
  }
}
