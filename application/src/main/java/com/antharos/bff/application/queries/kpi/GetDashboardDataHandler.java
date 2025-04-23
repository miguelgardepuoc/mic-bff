package com.antharos.bff.application.queries.kpi;

import com.antharos.bff.application.model.*;
import com.antharos.bff.application.model.DepartmentEmployees;
import com.antharos.bff.application.model.DepartmentSalary;
import com.antharos.bff.application.model.JobTitleEmployees;
import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.AnalyticsRepository;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
      var jobTitlesFuture =
          CompletableFuture.supplyAsync(
              corporateOrganizationRepository::findJobTitles, dashboardExecutor);
      var employeeCounts =
          CompletableFuture.supplyAsync(
              analyticsRepository::getEmployeesByMonth, dashboardExecutor);
      var salaryCosts =
          CompletableFuture.supplyAsync(analyticsRepository::getSalaryByMonth, dashboardExecutor);
      var deptEmployees =
          CompletableFuture.supplyAsync(
              analyticsRepository::getEmployeesByDepartment, dashboardExecutor);
      var deptSalaries =
          CompletableFuture.supplyAsync(
              analyticsRepository::getSalaryByDepartment, dashboardExecutor);
      var jobTitleEmployees =
          CompletableFuture.supplyAsync(
              analyticsRepository::getEmployeesByJobTitle, dashboardExecutor);

      CompletableFuture.allOf(
              employeeCounts, salaryCosts, deptEmployees, deptSalaries, jobTitleEmployees)
          .join();

      Map<String, String> departmentNameById =
          departmentsFuture.get().stream()
              .collect(Collectors.toMap(Department::getId, Department::getDescription));

      Map<String, String> jobTitleNameById =
          jobTitlesFuture.get().stream()
              .collect(Collectors.toMap(JobTitle::getId, JobTitle::getDescription));

      long totalEmployees =
          deptEmployees.get().stream()
              .mapToLong(com.antharos.bff.domain.report.DepartmentEmployees::totalEmployees)
              .sum();
      long totalSalary =
          deptSalaries.get().stream()
              .mapToLong(com.antharos.bff.domain.report.DepartmentSalary::totalSalary)
              .sum();
      long totalJobTitleEmployees =
          jobTitleEmployees.get().stream()
              .mapToLong(com.antharos.bff.domain.report.JobTitleEmployees::totalEmployees)
              .sum();

      return new DashboardData(
          employeeCounts.get(),
          salaryCosts.get(),
          mapDepartments(deptEmployees.get(), departmentNameById, totalEmployees),
          mapDepartmentSalaries(deptSalaries.get(), departmentNameById, totalSalary),
          mapJobTitles(jobTitleEmployees.get(), jobTitleNameById, totalJobTitleEmployees));

    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Failed to retrieve dashboard data", e);
    }
  }

  private List<DepartmentEmployees> mapDepartments(
      List<com.antharos.bff.domain.report.DepartmentEmployees> data,
      Map<String, String> nameMap,
      long totalEmployees) {
    return data.stream()
        .map(
            de -> {
              String departmentName =
                  nameMap.getOrDefault(de.departmentId().toString(), "Unknown Department");
              double percentage =
                  (totalEmployees == 0) ? 0 : Math.round((double) de.totalEmployees() / totalEmployees * 100);
              return new DepartmentEmployees(departmentName, (int) Math.round(percentage));
            })
        .toList();
  }

  private List<DepartmentSalary> mapDepartmentSalaries(
      List<com.antharos.bff.domain.report.DepartmentSalary> data,
      Map<String, String> nameMap,
      long totalSalary) {
    return data.stream()
        .map(
            ds -> {
              String departmentName =
                  nameMap.getOrDefault(ds.departmentId().toString(), "Unknown Department");
              double percentage =
                  (totalSalary == 0) ? 0 : Math.round((double) ds.totalSalary() / totalSalary * 100);
              return new DepartmentSalary(departmentName, (int) Math.round(percentage));
            })
        .toList();
  }

  private List<JobTitleEmployees> mapJobTitles(
      List<com.antharos.bff.domain.report.JobTitleEmployees> data,
      Map<String, String> nameMap,
      long totalJobTitleEmployees) {
    return data.stream()
        .map(
            jt -> {
              String jobTitleName =
                  nameMap.getOrDefault(jt.jobTitleId().toString(), "Unknown Job Title");
              double percentage =
                  (totalJobTitleEmployees == 0)
                      ? 0
                      : Math.round((double) jt.totalEmployees() / totalJobTitleEmployees * 100);
              return new JobTitleEmployees(jobTitleName, (int) Math.round(percentage));
            })
        .toList();
  }
}
