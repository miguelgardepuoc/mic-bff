package com.antharos.bff.application.update;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DisableDepartmentCommandHandler {

  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void handle(DisableDepartmentCommand command) {
    this.corporateOrganizationRepository.disableDepartment(command.getDepartmentId());
  }
}
