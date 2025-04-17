package com.antharos.bff.infrastructure.repository;

import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.login.Login;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import com.antharos.bff.infrastructure.repository.model.LoginRequest;
import com.antharos.bff.infrastructure.repository.model.RegisterUserRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CorporateOrganizationRepositoryImpl implements CorporateOrganizationRepository {

  private final WebClient corporateWebClient;

  public CorporateOrganizationRepositoryImpl(
      @Qualifier("corporateWebClient") WebClient corporateWebClient) {
    this.corporateWebClient = corporateWebClient;
  }

  @Override
  public void hire(Employee newEmployee) {
    corporateWebClient.post().uri("/").bodyValue(newEmployee).retrieve().toBodilessEntity().block();
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
  public Login login(String username, String password) {
    return corporateWebClient
        .post()
        .uri("/auth/login")
        .bodyValue(new LoginRequest(username, password))
        .retrieve()
        .bodyToMono(Login.class)
        .block();
  }
}
