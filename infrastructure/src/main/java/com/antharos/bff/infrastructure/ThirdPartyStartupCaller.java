package com.antharos.bff.infrastructure;

import com.antharos.bff.domain.repository.AIIntegrationsRepository;
import com.antharos.bff.domain.repository.AnalyticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ThirdPartyStartupCaller {

  private final AnalyticsRepository analyticsRepository;
  private final AIIntegrationsRepository aiIntegrationsRepository;

  @EventListener(ApplicationReadyEvent.class)
  @Async
  public void analyticsStartup() {
    this.analyticsRepository.getEmployeesByJobTitle();
  }

  @EventListener(ApplicationReadyEvent.class)
  @Async
  public void aiintegrationStartup() {
    this.aiIntegrationsRepository.health();
  }
}
