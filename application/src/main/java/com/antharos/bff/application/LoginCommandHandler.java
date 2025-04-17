package com.antharos.bff.application;

import com.antharos.bff.domain.login.Login;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public Login handle(LoginCommand command) {
    return this.corporateOrganizationRepository.login(command.getUsername(), command.getPassword());
  }
}
