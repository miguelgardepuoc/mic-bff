package com.antharos.bff.application.commands.login;

import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;
  private final AuthenticationManager authenticationManager;

  public Employee handle(LoginCommand command) {
    this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));

    return this.corporateOrganizationRepository
        .findByUsername(command.getUsername())
        .orElseThrow(() -> new BadCredentialsException(command.getUsername()));
  }
}
