package com.antharos.bff.infrastructure.in.dto.report;

import com.antharos.bff.domain.report.*;

import java.util.List;

public record DashboardResponse(
    List<MonthlyEmployeeCount> employeeCounts,
    List<MonthlySalaryCost> salaryCosts,
    List<DepartmentEmployees> departmentEmployees,
    List<DepartmentSalary> departmentSalaries,
    List<JobTitleEmployees> jobTitleEmployees
) {}
