package com.antharos.bff.application.create;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateDepartmentCommandHandler {

  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void doHandle(final CreateDepartmentCommand command) {
    this.corporateOrganizationRepository.createDepartment(
        command.getId(), command.getDescription());
  }
}
