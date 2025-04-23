package com.antharos.bff.application.queries.kpi;

import com.antharos.bff.application.model.*;
import com.antharos.bff.application.model.DepartmentEmployees;
import com.antharos.bff.application.model.DepartmentSalary;
import com.antharos.bff.application.model.JobTitleEmployees;
import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.AnalyticsRepository;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetDashboardDataHandler {

    private final AnalyticsRepository analyticsRepository;
    private final CorporateOrganizationRepository corporateOrganizationRepository;
    private final Executor dashboardExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Async
    public CompletableFuture<List<Department>> findDepartments() {
        return CompletableFuture.completedFuture(corporateOrganizationRepository.findAll());
    }

    public DashboardData handle(GetDashboardDataQuery query) {
        try {
            var departmentsFuture = findDepartments();
            var jobTitlesFuture = CompletableFuture.supplyAsync(corporateOrganizationRepository::findJobTitles, dashboardExecutor);
            var employeeCounts = CompletableFuture.supplyAsync(analyticsRepository::getEmployeesByMonth, dashboardExecutor);
            var salaryCosts = CompletableFuture.supplyAsync(analyticsRepository::getSalaryByMonth, dashboardExecutor);
            var deptEmployees = CompletableFuture.supplyAsync(analyticsRepository::getEmployeesByDepartment, dashboardExecutor);
            var deptSalaries = CompletableFuture.supplyAsync(analyticsRepository::getSalaryByDepartment, dashboardExecutor);
            var jobTitleEmployees = CompletableFuture.supplyAsync(analyticsRepository::getEmployeesByJobTitle, dashboardExecutor);

            CompletableFuture.allOf(employeeCounts, salaryCosts, deptEmployees, deptSalaries, jobTitleEmployees).join();

            Map<String, String> departmentNameById = departmentsFuture.get().stream()
                    .collect(Collectors.toMap(Department::getId, Department::getDescription));

            Map<String, String> jobTitleNameById = jobTitlesFuture.get().stream()
                    .collect(Collectors.toMap(JobTitle::getId, JobTitle::getDescription));

            return new DashboardData(
                    employeeCounts.get(),
                    salaryCosts.get(),
                    mapDepartments(deptEmployees.get(), departmentNameById),
                    mapDepartmentSalaries(deptSalaries.get(), departmentNameById),
                    mapJobTitles(jobTitleEmployees.get(), jobTitleNameById)
            );

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Failed to retrieve dashboard data", e);
        }
    }

    private List<DepartmentEmployees> mapDepartments(List<com.antharos.bff.domain.report.DepartmentEmployees> data, Map<String, String> nameMap) {
        return data.stream()
                .map(de -> new DepartmentEmployees(
                        nameMap.getOrDefault(de.departmentId().toString(), "Unknown Department"),
                        de.totalEmployees()))
                .toList();
    }

    private List<DepartmentSalary> mapDepartmentSalaries(List<com.antharos.bff.domain.report.DepartmentSalary> data, Map<String, String> nameMap) {
        return data.stream()
                .map(ds -> new DepartmentSalary(
                        nameMap.getOrDefault(ds.departmentId().toString(), "Unknown Department"),
                        ds.totalSalary()))
                .toList();
    }

    private List<JobTitleEmployees> mapJobTitles(List<com.antharos.bff.domain.report.JobTitleEmployees> data, Map<String, String> nameMap) {
        return data.stream()
                .map(jt -> new JobTitleEmployees(
                        nameMap.getOrDefault(jt.jobTitleId().toString(), "Unknown Job Title"),
                        jt.totalEmployees()))
                .toList();
    }
}
