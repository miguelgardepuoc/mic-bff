package com.antharos.bff.infrastructure.in.dto.report;

import com.antharos.bff.application.model.DepartmentEmployees;
import com.antharos.bff.application.model.DepartmentSalary;
import com.antharos.bff.application.model.JobTitleEmployees;
import com.antharos.bff.domain.report.MonthlyEmployeeCount;
import com.antharos.bff.domain.report.MonthlySalaryCost;
import java.util.List;

public record DashboardResponse(
    List<MonthlyEmployeeCount> employeeCounts,
    List<MonthlySalaryCost> salaryCosts,
    List<DepartmentEmployees> departmentEmployees,
    List<DepartmentSalary> departmentSalaries,
    List<JobTitleEmployees> jobTitleEmployees) {}
