package com.antharos.bff.application.find;

import com.antharos.bff.application.model.SimpleJobOfferWithTitle;
import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import com.antharos.bff.domain.jobtitle.JobTitle;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindJobOffersQueryHandler {
  private final JobOfferRepository jobOfferRepository;
  private final CorporateOrganizationRepository corporateOrganizationRepository;

  public List<SimpleJobOfferWithTitle> handle(FindJobOffersQuery query) {
    List<SimpleJobOffer> simpleJobOffers = this.jobOfferRepository.findAll();
    List<JobTitle> jobTitles = this.corporateOrganizationRepository.findJobTitles();

    Map<UUID, JobTitle> jobTitleMap =
        jobTitles.stream().collect(Collectors.toMap(JobTitle::getId, jt -> jt));

    return simpleJobOffers.stream()
        .map(
            offer -> {
              JobTitle jobTitle = jobTitleMap.get(offer.jobTitleId());
              String description = jobTitle != null ? jobTitle.getDescription() : "Unknown";
              String photoUrl = jobTitle != null ? jobTitle.getPhotoUrl() : null;

              return new SimpleJobOfferWithTitle(
                  offer.id(), description, photoUrl, offer.minSalary(), offer.maxSalary());
            })
        .toList();
  }
}
