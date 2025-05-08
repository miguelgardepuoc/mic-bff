package com.antharos.bff.application.queries.employee;

import com.antharos.bff.application.model.SimpleEmployee;
import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeesQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<SimpleEmployee> handle() {
    List<Department> departments = this.corporateOrganizationRepository.findAll();
    List<JobTitle> jobTitles = this.corporateOrganizationRepository.findJobTitles();
    List<Employee> employees = this.corporateOrganizationRepository.findAllEmployees();

    Map<String, String> departmentNameById =
        departments.stream()
            .collect(Collectors.toMap(Department::getId, Department::getDescription));

    Map<String, String> jobTitleNameById =
        jobTitles.stream().collect(Collectors.toMap(JobTitle::getId, JobTitle::getDescription));

    return employees.stream()
        .map(
            emp ->
                new SimpleEmployee(
                    emp.id(),
                    emp.employeeNumber(),
                    emp.username(),
                    emp.name() + " " + emp.surname(),
                    emp.salary(),
                    emp.dni(),
                    jobTitleNameById.getOrDefault(emp.jobTitleId(), "Unknown"),
                    emp.hiringDate(),
                    departmentNameById.getOrDefault(emp.departmentId(), "Unknown"),
                    emp.status()))
        .sorted(Comparator.comparing(SimpleEmployee::employeeNumber))
        .collect(Collectors.toList());
  }
}
