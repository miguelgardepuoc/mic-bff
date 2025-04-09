package com.antharos.bff.infrastructure.repository;

import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

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
}
