package com.antharos.bff.domain.repository;

import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import java.util.List;
import java.util.Optional;

public interface CorporateOrganizationRepository {

  void hire(Employee employee);

  List<JobTitle> findJobTitles();

  void signUp(String username, String password);

  List<Department> findAll();

  List<Employee> findAllEmployees();

  void disableDepartment(String departmentId);

  void renameDepartment(String departmentId, String description);

  void createDepartment(String id, String description);

  void terminateEmployee(String userId);

  void putEmployeeOnLeave(String userId);

  void markEmployeeAsInactive(String userId);

  Optional<Employee> findByUsername(String username);

  Optional<Employee> findByUserId(String departmentHeadUserId);

  void updateDepartmentHead(String id, String username);
}
