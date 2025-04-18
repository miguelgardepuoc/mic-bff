package com.antharos.bff.application.find;

import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FindDepartmentsQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<Department> handle(FindDepartmentsQuery query) {
    return this.corporateOrganizationRepository.findAll();
  }
}
