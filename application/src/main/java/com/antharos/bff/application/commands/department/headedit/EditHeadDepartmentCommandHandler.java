package com.antharos.bff.application.commands.department.headedit;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EditHeadDepartmentCommandHandler {

  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void handle(EditHeadDepartmentCommand command) {
    this.corporateOrganizationRepository.updateDepartmentHead(
        command.getId(), command.getUsername());
  }
}
