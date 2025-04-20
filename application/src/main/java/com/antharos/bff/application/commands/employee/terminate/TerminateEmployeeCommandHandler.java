package com.antharos.bff.application.commands.employee.terminate;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TerminateEmployeeCommandHandler {

  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void doHandle(final TerminateEmployeeCommand command) {
    this.corporateOrganizationRepository.terminateEmployee(command.getUserId());
  }
}
