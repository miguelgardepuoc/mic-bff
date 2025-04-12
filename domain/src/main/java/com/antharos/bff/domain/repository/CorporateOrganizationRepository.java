package com.antharos.bff.domain.repository;

import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.jobtitle.JobTitle;
import java.util.List;

public interface CorporateOrganizationRepository {

  void hire(Employee employee);

  List<JobTitle> findJobTitles();
}
