package com.antharos.bff.infrastructure.out.repository;

import com.antharos.bff.domain.repository.AIIntegrationsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AIIntegrationsRepositoryImpl implements AIIntegrationsRepository {

  private final WebClient webClient;

  public AIIntegrationsRepositoryImpl(
      @Qualifier("aiintegrationsWebClient") WebClient aiintegrationsWebClient) {
    this.webClient = aiintegrationsWebClient;
  }

  @Override
  public void health() {
    this.webClient.get().uri("/health").retrieve().toBodilessEntity().block();
  }
}
