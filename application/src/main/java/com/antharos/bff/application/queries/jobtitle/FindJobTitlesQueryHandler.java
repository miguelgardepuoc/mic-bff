package com.antharos.bff.application.queries.jobtitle;

import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindJobTitlesQueryHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;
  private final JobOfferRepository jobOfferRepository;

  public List<JobTitle> handle() {
    List<SimpleJobOffer> simpleJobOffers = this.jobOfferRepository.findAll();
    List<JobTitle> jobTitles = this.corporateOrganizationRepository.findJobTitles();

    Set<String> jobTitleIds =
        simpleJobOffers.stream().map(SimpleJobOffer::jobTitleId).collect(Collectors.toSet());

    return jobTitles.stream()
        .filter(jobTitle -> !jobTitleIds.contains(jobTitle.getId()))
        .collect(Collectors.toList());
  }
}
