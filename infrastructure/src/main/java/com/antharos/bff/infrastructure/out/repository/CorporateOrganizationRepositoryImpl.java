package com.antharos.bff.infrastructure.out.repository;

import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import com.antharos.bff.infrastructure.in.util.ErrorResponse;
import com.antharos.bff.infrastructure.in.util.FieldError;
import com.antharos.bff.infrastructure.out.repository.exception.DepartmentHasActiveEmployeesException;
import com.antharos.bff.infrastructure.out.repository.exception.DepartmentHeadAssignationException;
import com.antharos.bff.infrastructure.out.repository.exception.HiringValidationException;
import com.antharos.bff.infrastructure.out.repository.model.RegisterUserRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CorporateOrganizationRepositoryImpl implements CorporateOrganizationRepository {

  private final WebClient corporateWebClient;

  public CorporateOrganizationRepositoryImpl(
      @Qualifier("corporateWebClient") WebClient corporateWebClient) {
    this.corporateWebClient = corporateWebClient;
  }

  @Override
  public void hire(Employee newEmployee) {
    this.corporateWebClient
        .post()
        .uri("/employees/hiring")
        .bodyValue(newEmployee)
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            response ->
                response
                    .bodyToMono(ErrorResponse.class)
                    .flatMap(
                        errorResponse -> Mono.error(new HiringValidationException(errorResponse))))
        .toBodilessEntity()
        .block();
  }

  @Override
  public List<JobTitle> findJobTitles() {
    return this.corporateWebClient
        .get()
        .uri("/job-titles")
        .retrieve()
        .bodyToFlux(JobTitle.class)
        .collectList()
        .block();
  }

  @Override
  public void signUp(String username, String password) {
    this.corporateWebClient
        .post()
        .uri("/auth/signup")
        .bodyValue(new RegisterUserRequest(username, password))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public List<Department> findAll() {
    return this.corporateWebClient
        .get()
        .uri("/departments")
        .retrieve()
        .bodyToFlux(Department.class)
        .collectList()
        .block();
  }

  @Override
  public List<Employee> findAllEmployees() {
    return this.corporateWebClient
        .get()
        .uri("/employees")
        .retrieve()
        .bodyToFlux(Employee.class)
        .collectList()
        .block();
  }

  @Override
  public void disableDepartment(String departmentId) {
    this.corporateWebClient
        .delete()
        .uri("/departments/{id}", departmentId)
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            response ->
                response
                    .bodyToMono(ErrorResponse.class)
                    .flatMap(
                        errorResponse ->
                            Mono.error(new DepartmentHasActiveEmployeesException(errorResponse))))
        .toBodilessEntity()
        .block();
  }

  @Override
  public void renameDepartment(String departmentId, String description) {
    this.corporateWebClient
        .patch()
        .uri("/departments/{id}/renaming", departmentId)
        .bodyValue(new Department(departmentId, description, null))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void createDepartment(String id, String description) {
    this.corporateWebClient
        .post()
        .uri("/departments")
        .bodyValue(new Department(id, description, null))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void terminateEmployee(String userId) {
    this.corporateWebClient
        .patch()
        .uri("/employees/{id}/termination", userId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void putEmployeeOnLeave(String userId) {
    this.corporateWebClient
        .patch()
        .uri("/employees/{id}/on-leave", userId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void markEmployeeAsInactive(String userId) {
    this.corporateWebClient
        .patch()
        .uri("/employees/{id}/inactivation", userId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public Optional<Employee> findByUsername(String username) {
    try {
      Employee employee =
          this.corporateWebClient
              .get()
              .uri("/employees/username/{username}", username)
              .retrieve()
              .onStatus(
                  HttpStatusCode::is4xxClientError,
                  response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                      return Mono.empty();
                    }
                    return response.createException();
                  })
              .bodyToMono(Employee.class)
              .block();

      return Optional.ofNullable(employee);
    } catch (Exception e) {
      throw new RuntimeException("Error retrieving employee by username", e);
    }
  }

  @Override
  public Optional<Employee> findByUserId(String employeeId) {
    try {
      Employee employee =
          this.corporateWebClient
              .get()
              .uri("/employees/{id}", employeeId)
              .retrieve()
              .onStatus(
                  HttpStatusCode::is4xxClientError,
                  response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                      return Mono.empty();
                    }
                    return response.createException();
                  })
              .bodyToMono(Employee.class)
              .block();

      return Optional.ofNullable(employee);
    } catch (Exception e) {
      throw new RuntimeException("Error retrieving employee by id", e);
    }
  }

  @Override
  public void updateDepartmentHead(String id, String username) {
    this.corporateWebClient
        .put()
        .uri("/departments/{id}/head", id)
        .bodyValue(Collections.singletonMap("username", username))
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            response ->
                response
                    .bodyToMono(ErrorResponse.class)
                    .defaultIfEmpty(
                        new ErrorResponse(
                            List.of(new FieldError("", "", "No error details provided"))))
                    .flatMap(
                        errorResponse ->
                            Mono.error(new DepartmentHeadAssignationException(errorResponse))))
        .toBodilessEntity()
        .block();
  }
}
