package com.antharos.bff.infrastructure.repository;

import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CorporateOrganizationRepositoryImpl implements CorporateOrganizationRepository {
  @Value("${rest-client.corporate-organization.host}")
  private String corporateOrganizationApiUrl;

  private final RestTemplate restTemplate;

  public CorporateOrganizationRepositoryImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void hire(Employee newEmployee) {
    this.restTemplate.postForObject(this.corporateOrganizationApiUrl, newEmployee, Void.class);
  }

  @Override
  public List<JobTitle> findJobTitles() {
    String jobTitlesContextPath = "/job-titles";
    ResponseEntity<List<JobTitle>> response =
        this.restTemplate.exchange(
            corporateOrganizationApiUrl + jobTitlesContextPath,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {});
    return response.getBody();
  }
}
