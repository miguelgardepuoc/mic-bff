package com.antharos.bff.domain.repository;

import com.antharos.bff.domain.report.*;
import java.util.List;

public interface AnalyticsRepository {

  List<MonthlyEmployeeCount> getEmployeesByMonth();

  List<MonthlySalaryCost> getSalaryByMonth();

  List<DepartmentEmployees> getEmployeesByDepartment();

  List<DepartmentSalary> getSalaryByDepartment();

  List<JobTitleEmployees> getEmployeesByJobTitle();
}
