package com.antharos.bff.infrastructure.config;

import com.antharos.bff.domain.globalexceptions.AlreadyExistsException;
import com.antharos.bff.infrastructure.config.security.jwt.JwtTokenInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class RestConfig {

  @Value("${rest-client.corporate-organization.host}")
  private String corporateOrganizationApiUrl;

  @Value("${rest-client.job-offer.host}")
  private String jobOfferApiUrl;

  @Value("${rest-client.analytics.host}")
  private String analyticsApiUrl;

  @Value("${rest-client.aiintegrations.host}")
  private String aiintegrationsApiUrl;

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
        .filter(RestConfig.errorHandlingFilter())
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
        .filter(RestConfig.errorHandlingFilter())
        .build();
  }

  @Bean
  @Qualifier("analyticsWebClient")
  public WebClient analyticsWebClient(WebClient.Builder builder) {
    return builder
        .baseUrl(analyticsApiUrl)
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
        .filter(RestConfig.errorHandlingFilter())
        .build();
  }

  @Bean
  @Qualifier("aiintegrationsWebClient")
  public WebClient aiintegrationsWebClient(WebClient.Builder builder) {
    return builder
        .baseUrl(aiintegrationsApiUrl)
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
        .filter(RestConfig.errorHandlingFilter())
        .build();
  }

  public static ExchangeFilterFunction errorHandlingFilter() {
    return ExchangeFilterFunction.ofResponseProcessor(
        clientResponse -> {
          if (clientResponse.statusCode() == HttpStatus.CONFLICT) {
            return clientResponse
                .bodyToMono(String.class)
                .flatMap(
                    body -> Mono.error(new AlreadyExistsException("409 from downstream: " + body)));
          }
          return Mono.just(clientResponse);
        });
  }
}
