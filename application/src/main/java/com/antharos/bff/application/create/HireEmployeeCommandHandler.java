package com.antharos.bff.application.create;

import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HireEmployeeCommandHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public void doHandle(final HireEmployeeCommand command) {
    final Employee employee =
        new Employee(
            command.getUserId(),
            command.getDni(),
            command.getName(),
            command.getSurname(),
            command.getTelephoneNumber(),
            command.getSalary(),
            command.getDepartmentId(),
            command.getHiringDate(),
            command.getRole(),
            command.getJobTitleId());
    this.corporateOrganizationRepository.hire(employee);
  }
}
