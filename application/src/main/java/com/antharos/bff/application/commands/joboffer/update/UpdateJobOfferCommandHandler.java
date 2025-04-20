package com.antharos.bff.application.commands.joboffer.update;

import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateJobOfferCommandHandler {

  private final JobOfferRepository jobOfferRepository;

  public void doHandle(final UpdateJobOfferCommand command) {
    final JobOffer jobOffer =
        new JobOffer(
            command.getId(),
            null,
            command.getMinSalary(),
            command.getMaxSalary(),
            command.getRemote(),
            command.getDescription(),
            command.getRequirement());
    this.jobOfferRepository.update(jobOffer);
  }
}
