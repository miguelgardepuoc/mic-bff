package com.antharos.bff.infrastructure.out.repository;

import com.antharos.bff.domain.department.Department;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.login.Login;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import com.antharos.bff.infrastructure.in.util.ErrorResponse;
import com.antharos.bff.infrastructure.out.repository.exception.HiringValidationException;
import com.antharos.bff.infrastructure.out.repository.model.LoginRequest;
import com.antharos.bff.infrastructure.out.repository.model.RegisterUserRequest;
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
    return corporateWebClient
        .get()
        .uri("/job-titles")
        .retrieve()
        .bodyToFlux(JobTitle.class)
        .collectList()
        .block();
  }

  @Override
  public void signUp(String username, String password) {
    corporateWebClient
        .post()
        .uri("/auth/signup")
        .bodyValue(new RegisterUserRequest(username, password))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public List<Department> findAll() {
    return corporateWebClient
        .get()
        .uri("/departments")
        .retrieve()
        .bodyToFlux(Department.class)
        .collectList()
        .block();
  }

  @Override
  public List<Employee> findAllEmployees() {
    return corporateWebClient
        .get()
        .uri("/employees")
        .retrieve()
        .bodyToFlux(Employee.class)
        .collectList()
        .block();
  }

  @Override
  public void disableDepartment(String departmentId) {
    corporateWebClient
        .delete()
        .uri("/departments/{id}", departmentId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void renameDepartment(String departmentId, String description) {
    corporateWebClient
        .patch()
        .uri("/departments/{id}/renaming", departmentId)
        .bodyValue(new Department(departmentId, description))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void createDepartment(String id, String description) {
    corporateWebClient
        .post()
        .uri("/departments")
        .bodyValue(new Department(id, description))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void terminateEmployee(String userId) {
    corporateWebClient
        .patch()
        .uri("/employees/{id}/termination", userId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void putEmployeeOnLeave(String userId) {
    corporateWebClient
        .patch()
        .uri("/employees/{id}/on-leave", userId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void markEmployeeAsInactive(String userId) {
    corporateWebClient
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
}
