package com.antharos.bff.application.queries.jobtitle;

import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindJobTitlesQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<JobTitle> handle() {
    return this.corporateOrganizationRepository.findJobTitles();
  }
}
