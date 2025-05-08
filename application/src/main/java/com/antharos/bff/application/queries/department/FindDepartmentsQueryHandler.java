package com.antharos.bff.application.queries.department;

import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindDepartmentsQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<com.antharos.bff.application.model.Department> handle() {
    var departments = corporateOrganizationRepository.findAll();

    return departments.stream()
        .map(
            department -> {
              String departmentHeadName = "-";

              if (department.getDepartmentHeadUserId() != null) {
                var employeeOpt =
                    corporateOrganizationRepository.findByUserId(
                        department.getDepartmentHeadUserId());
                departmentHeadName =
                    employeeOpt.map(e -> e.name() + " " + e.surname()).orElse("Unknown");
              }

              return new com.antharos.bff.application.model.Department(
                  department.getId(), department.getDescription(), departmentHeadName);
            })
        .toList();
  }
}
