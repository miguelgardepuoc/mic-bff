package com.antharos.bff.application.find;

import com.antharos.bff.application.model.SimpleEmployee;
import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeesQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<SimpleEmployee> handle(FindEmployeesQuery findEmployeesQuery) {
    List<Department> departments = this.corporateOrganizationRepository.findAll();
    List<JobTitle> jobTitles = this.corporateOrganizationRepository.findJobTitles();
    List<Employee> employees = this.corporateOrganizationRepository.findAllEmployees();

    Map<UUID, String> departmentNameById =
        departments.stream()
            .collect(Collectors.toMap(Department::getId, Department::getDescription));

    Map<UUID, String> jobTitleNameById =
        jobTitles.stream().collect(Collectors.toMap(JobTitle::getId, JobTitle::getDescription));

    return employees.stream()
        .map(
            emp ->
                new SimpleEmployee(
                    emp.id(),
                    emp.employeeNumber(),
                    emp.name() + " " + emp.surname(),
                    emp.salary(),
                    emp.dni(),
                    jobTitleNameById.getOrDefault(UUID.fromString(emp.jobTitleId()), "Unknown"),
                    emp.hiringDate(),
                    departmentNameById.getOrDefault(UUID.fromString(emp.departmentId()), "Unknown"),
                    emp.status()))
        .collect(Collectors.toList());
  }
}
