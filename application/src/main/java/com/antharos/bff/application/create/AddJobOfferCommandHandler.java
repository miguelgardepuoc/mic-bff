package com.antharos.bff.application.create;

import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddJobOfferCommandHandler {

  private final JobOfferRepository jobOfferRepository;

  public void doHandle(final AddJobOfferCommand command) {
    final JobOffer jobOffer =
        new JobOffer(
            command.getId(),
            command.getJobTitleId(),
            command.getMinSalary(),
            command.getMaxSalary(),
            command.getRemote(),
            command.getDescription(),
            command.getRequirement());
    this.jobOfferRepository.addJobOffer(jobOffer);
  }
}
