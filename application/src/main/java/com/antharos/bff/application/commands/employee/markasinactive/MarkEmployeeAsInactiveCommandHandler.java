package com.antharos.bff.application.commands.employee.markasinactive;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarkEmployeeAsInactiveCommandHandler {

  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void doHandle(final MarkEmployeeAsInactiveCommand command) {
    this.corporateOrganizationRepository.markEmployeeAsInactive(command.getUserId());
  }
}
