package com.antharos.bff.application.find;

import com.antharos.bff.application.model.JobOfferWithTitle;
import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindJobOfferQueryHandler {
  private final JobOfferRepository jobOfferRepository;
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public Optional<JobOfferWithTitle> handle(FindJobOfferQuery query) {
    JobOffer jobOffer = this.jobOfferRepository.findById(query.getJobOfferId());
    List<JobTitle> jobTitles = this.corporateOrganizationRepository.findJobTitles();

    Map<UUID, JobTitle> jobTitleMap =
        jobTitles.stream().collect(Collectors.toMap(JobTitle::getId, jt -> jt));

    JobTitle jobTitle = jobTitleMap.get(jobOffer.jobTitleId());
    String description = jobTitle != null ? jobTitle.getDescription() : "Unknown";
    String photoUrl = jobTitle != null ? jobTitle.getPhotoUrl() : null;

    return Optional.of(
        new JobOfferWithTitle(
            jobOffer.id(),
            description,
            photoUrl,
            jobOffer.minSalary(),
            jobOffer.maxSalary(),
            jobOffer.remote(),
            jobOffer.description(),
            jobOffer.requirement()));
  }
}
