package com.antharos.bff.domain.repository;

import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.login.Login;
import java.util.List;

public interface CorporateOrganizationRepository {

  void hire(Employee employee);

  List<JobTitle> findJobTitles();

  void signUp(String username, String password);

  Login login(String username, String password);

  List<Department> findAll();

  List<Employee> findAllEmployees();

  void disableDepartment(String departmentId);

  void renameDepartment(String departmentId, String description);

  void createDepartment(String id, String description);
}
