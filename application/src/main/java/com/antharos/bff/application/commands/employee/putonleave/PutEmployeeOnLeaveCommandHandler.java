package com.antharos.bff.application.commands.employee.putonleave;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutEmployeeOnLeaveCommandHandler {

  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void doHandle(final PutEmployeeOnLeaveCommand command) {
    this.corporateOrganizationRepository.putEmployeeOnLeave(command.getUserId());
  }
}
