package com.antharos.bff.application.queries.department;

import com.antharos.bff.application.model.Department;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindDepartmentsQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<Department> handle() {
    var departments = this.corporateOrganizationRepository.findAll();

    return departments.stream()
        .map(
            department -> {
              String departmentHeadName = "-";

              if (department.getDepartmentHeadUserId() != null) {
                var employeeOpt =
                    this.corporateOrganizationRepository.findByUserId(
                        department.getDepartmentHeadUserId());
                departmentHeadName =
                    employeeOpt.map(e -> e.name() + " " + e.surname()).orElse("Unknown");
              }

              return new Department(
                  department.getId(), department.getDescription(), departmentHeadName);
            })
        .toList();
  }
}
