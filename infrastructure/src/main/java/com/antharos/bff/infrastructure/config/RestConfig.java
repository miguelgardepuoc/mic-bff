package com.antharos.bff.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestConfig {

  @Value("${rest-client.corporate-organization.host}")
  private String corporateOrganizationApiUrl;

  @Value("${rest-client.job-offer.host}")
  private String jobOfferApiUrl;

  @Bean
  @Qualifier("corporateWebClient")
  public WebClient corporateWebClient(WebClient.Builder builder) {
    return builder
        .baseUrl(corporateOrganizationApiUrl)
        .filter(
            (request, next) -> {
              String token = JwtTokenInterceptor.getToken();
              if (token != null) {
                ClientRequest newRequest =
                    ClientRequest.from(request).header(HttpHeaders.AUTHORIZATION, token).build();
                return next.exchange(newRequest);
              } else {
                return next.exchange(request);
              }
            })
        .build();
  }

  @Bean
  @Qualifier("jobOfferWebClient")
  public WebClient jobOfferWebClient(WebClient.Builder builder) {
    return builder
        .baseUrl(jobOfferApiUrl)
        .filter(
            (request, next) -> {
              String token = JwtTokenInterceptor.getToken();
              if (token != null) {
                ClientRequest newRequest =
                    ClientRequest.from(request).header(HttpHeaders.AUTHORIZATION, token).build();
                return next.exchange(newRequest);
              } else {
                return next.exchange(request);
              }
            })
        .build();
  }
}
