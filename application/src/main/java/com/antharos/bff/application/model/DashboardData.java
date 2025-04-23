package com.antharos.bff.application.model;

import com.antharos.bff.domain.report.*;

import java.util.List;

public record DashboardData(
    List<MonthlyEmployeeCount> employeeCounts,
    List<MonthlySalaryCost> salaryCosts,
    List<DepartmentEmployees> departmentEmployees,
    List<DepartmentSalary> departmentSalaries,
    List<JobTitleEmployees> jobTitleEmployees
) {}
