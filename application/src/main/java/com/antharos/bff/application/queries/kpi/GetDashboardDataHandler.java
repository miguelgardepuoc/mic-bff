package com.antharos.bff.application.queries.kpi;

import com.antharos.bff.application.model.*;
import com.antharos.bff.application.model.DepartmentEmployees;
import com.antharos.bff.application.model.DepartmentSalary;
import com.antharos.bff.application.model.JobTitleEmployees;
import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.report.MonthlyEmployeeCount;
import com.antharos.bff.domain.report.MonthlySalaryCost;
import com.antharos.bff.domain.repository.AnalyticsRepository;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.ArrayList;
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
    return CompletableFuture.completedFuture(this.corporateOrganizationRepository.findAll());
  }

  @Async
  public CompletableFuture<List<MonthlySalaryCost>> findSalaryCosts() {
    return CompletableFuture.completedFuture(this.analyticsRepository.getSalaryByMonth());
  }

  @Async
  public CompletableFuture<List<MonthlyEmployeeCount>> findEmployeeCounts() {
    return CompletableFuture.completedFuture(this.analyticsRepository.getEmployeesByMonth());
  }

  @Async
  public CompletableFuture<List<com.antharos.bff.domain.report.DepartmentEmployees>>
      findDeptEmployees() {
    return CompletableFuture.completedFuture(this.analyticsRepository.getEmployeesByDepartment());
  }

  @Async
  public CompletableFuture<List<com.antharos.bff.domain.report.DepartmentSalary>>
      findDeptSalaries() {
    return CompletableFuture.completedFuture(this.analyticsRepository.getSalaryByDepartment());
  }

  @Async
  public CompletableFuture<List<com.antharos.bff.domain.report.JobTitleEmployees>>
      findJobTitleEmployees() {
    return CompletableFuture.completedFuture(this.analyticsRepository.getEmployeesByJobTitle());
  }

  public DashboardData handle(GetDashboardDataQuery query) {
    try {
      var departmentsFuture = findDepartments();
      var jobTitlesFuture =
          CompletableFuture.supplyAsync(
              corporateOrganizationRepository::findJobTitles, dashboardExecutor);
      var employeeCounts = findEmployeeCounts();
      var salaryCosts = findSalaryCosts();
      var deptEmployees = findDeptEmployees();
      var deptSalaries = findDeptSalaries();
      var jobTitleEmployees = findJobTitleEmployees();

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

    if (totalEmployees == 0 || data.isEmpty()) {
      return data.stream()
          .map(
              de ->
                  new DepartmentEmployees(
                      nameMap.getOrDefault(de.departmentId().toString(), "Unknown Department"), 0))
          .toList();
    }

    List<RawEntry> rawEntries =
        data.stream()
            .map(
                de -> {
                  double rawPercentage = ((double) de.totalEmployees() / totalEmployees) * 100;
                  return new RawEntry(
                      nameMap.getOrDefault(de.departmentId().toString(), "Unknown Department"),
                      rawPercentage);
                })
            .toList();

    return roundAndAdjust(rawEntries).stream()
        .map(e -> new DepartmentEmployees(e.name(), e.roundedPercentage()))
        .toList();
  }

  private List<DepartmentSalary> mapDepartmentSalaries(
      List<com.antharos.bff.domain.report.DepartmentSalary> data,
      Map<String, String> nameMap,
      long totalSalary) {

    if (totalSalary == 0 || data.isEmpty()) {
      return data.stream()
          .map(
              ds ->
                  new DepartmentSalary(
                      nameMap.getOrDefault(ds.departmentId().toString(), "Unknown Department"), 0))
          .toList();
    }

    List<RawEntry> rawEntries =
        data.stream()
            .map(
                ds -> {
                  double rawPercentage = ((double) ds.totalSalary() / totalSalary) * 100;
                  return new RawEntry(
                      nameMap.getOrDefault(ds.departmentId().toString(), "Unknown Department"),
                      rawPercentage);
                })
            .toList();

    return roundAndAdjust(rawEntries).stream()
        .map(e -> new DepartmentSalary(e.name(), e.roundedPercentage()))
        .toList();
  }

  private List<JobTitleEmployees> mapJobTitles(
      List<com.antharos.bff.domain.report.JobTitleEmployees> data,
      Map<String, String> nameMap,
      long totalJobTitleEmployees) {

    if (totalJobTitleEmployees == 0 || data.isEmpty()) {
      return data.stream()
          .map(
              jt ->
                  new JobTitleEmployees(
                      nameMap.getOrDefault(jt.jobTitleId().toString(), "Unknown Job Title"), 0))
          .toList();
    }

    List<RawEntry> rawEntries =
        data.stream()
            .map(
                jt -> {
                  double rawPercentage =
                      ((double) jt.totalEmployees() / totalJobTitleEmployees) * 100;
                  return new RawEntry(
                      nameMap.getOrDefault(jt.jobTitleId().toString(), "Unknown Job Title"),
                      rawPercentage);
                })
            .toList();

    return roundAndAdjust(rawEntries).stream()
        .map(e -> new JobTitleEmployees(e.name(), e.roundedPercentage()))
        .toList();
  }

  private record RawEntry(String name, double rawPercentage) {}

  private record RoundedEntry(String name, int roundedPercentage) {}

  private List<RoundedEntry> roundAndAdjust(List<RawEntry> rawEntries) {
    int totalRounded = 0;
    List<RoundedEntry> rounded = new ArrayList<>();
    List<Double> fractions = new ArrayList<>();

    for (RawEntry e : rawEntries) {
      int roundedValue = (int) Math.floor(e.rawPercentage());
      rounded.add(new RoundedEntry(e.name(), roundedValue));
      totalRounded += roundedValue;
      fractions.add(e.rawPercentage() - roundedValue);
    }

    int remaining = 100 - totalRounded;
    while (remaining > 0) {
      int maxIndex = 0;
      for (int i = 1; i < fractions.size(); i++) {
        if (fractions.get(i) > fractions.get(maxIndex)) {
          maxIndex = i;
        }
      }
      RoundedEntry r = rounded.get(maxIndex);
      rounded.set(maxIndex, new RoundedEntry(r.name(), r.roundedPercentage() + 1));
      fractions.set(maxIndex, 0.0);
      remaining--;
    }

    return rounded;
  }
}
