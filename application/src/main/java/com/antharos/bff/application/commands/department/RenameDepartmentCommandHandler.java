package com.antharos.bff.application.commands.department;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RenameDepartmentCommandHandler {

  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void handle(RenameDepartmentCommand command) {
    this.corporateOrganizationRepository.renameDepartment(
        command.getDepartmentId(), command.getDescription());
  }
}
