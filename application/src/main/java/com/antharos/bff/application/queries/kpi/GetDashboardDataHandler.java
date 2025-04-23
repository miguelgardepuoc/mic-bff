package com.antharos.bff.application.queries.kpi;

import com.antharos.bff.application.model.DashboardData;
import com.antharos.bff.domain.report.*;
import com.antharos.bff.domain.repository.AnalyticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@AllArgsConstructor
@Service
public class GetDashboardDataHandler {
    private final AnalyticsRepository analyticsRepository;

    public DashboardData handle(GetDashboardDataQuery query) {
        Executor executor = Executors.newFixedThreadPool(6);

        CompletableFuture<List<MonthlyEmployeeCount>> employeeCountsFuture =
                CompletableFuture.supplyAsync(analyticsRepository::getEmployeesByMonth, executor);

        CompletableFuture<List<MonthlySalaryCost>> salaryCostsFuture =
                CompletableFuture.supplyAsync(analyticsRepository::getSalaryByMonth, executor);

        CompletableFuture<List<DepartmentEmployees>> departmentEmployeesFuture =
                CompletableFuture.supplyAsync(analyticsRepository::getEmployeesByDepartment, executor);

        CompletableFuture<List<DepartmentSalary>> departmentSalariesFuture =
                CompletableFuture.supplyAsync(analyticsRepository::getSalaryByDepartment, executor);

        CompletableFuture<List<JobTitleEmployees>> jobTitleEmployeesFuture =
                CompletableFuture.supplyAsync(analyticsRepository::getEmployeesByJobTitle, executor);

        CompletableFuture.allOf(
                employeeCountsFuture,
                salaryCostsFuture,
                departmentEmployeesFuture,
                departmentSalariesFuture,
                jobTitleEmployeesFuture
        ).join();

        try {
            return new DashboardData(
                    employeeCountsFuture.get(),
                    salaryCostsFuture.get(),
                    departmentEmployeesFuture.get(),
                    departmentSalariesFuture.get(),
                    jobTitleEmployeesFuture.get()
            );
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error retrieving dashboard data", e);
        }
    }
}
