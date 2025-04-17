package com.antharos.bff.application;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpCommandHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void handle(SignUpCommand command) {
    this.corporateOrganizationRepository.signUp(command.getUsername(), command.getPassword());
  }
}
