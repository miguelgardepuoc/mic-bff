package com.antharos.bff.infrastructure.out.repository;

import com.antharos.bff.domain.report.*;
import com.antharos.bff.domain.repository.AnalyticsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AnalyticsRepositoryImpl implements AnalyticsRepository {
  private final WebClient webClient;

  public AnalyticsRepositoryImpl(@Qualifier("analyticsWebClient") WebClient analyticsWebClient) {
    this.webClient = analyticsWebClient;
  }

  @Override
  public List<MonthlyEmployeeCount> getEmployeesByMonth() {
    return webClient
        .get()
        .uri("/kpi/employees/monthly")
        .retrieve()
        .bodyToFlux(MonthlyEmployeeCount.class)
        .collectList()
        .block();
  }

  @Override
  public List<MonthlySalaryCost> getSalaryByMonth() {
    return webClient
        .get()
        .uri("/kpi/salary/monthly")
        .retrieve()
        .bodyToFlux(MonthlySalaryCost.class)
        .collectList()
        .block();
  }

  @Override
  public List<DepartmentEmployees> getEmployeesByDepartment() {
    return webClient
        .get()
        .uri("/kpi/employees/department")
        .retrieve()
        .bodyToFlux(DepartmentEmployees.class)
        .collectList()
        .block();
  }

  @Override
  public List<DepartmentSalary> getSalaryByDepartment() {
    return webClient
        .get()
        .uri("/kpi/salary/department")
        .retrieve()
        .bodyToFlux(DepartmentSalary.class)
        .collectList()
        .block();
  }

  @Override
  public List<JobTitleEmployees> getEmployeesByJobTitle() {
    return webClient
        .get()
        .uri("/kpi/employees/job-title")
        .retrieve()
        .bodyToFlux(JobTitleEmployees.class)
        .collectList()
        .block();
  }
}
