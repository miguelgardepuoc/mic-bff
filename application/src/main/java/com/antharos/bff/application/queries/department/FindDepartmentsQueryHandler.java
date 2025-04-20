package com.antharos.bff.application.queries.department;

import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindDepartmentsQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<Department> handle() {
    return this.corporateOrganizationRepository.findAll();
  }
}
