package com.antharos.bff.application.update;

import com.antharos.bff.domain.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HireCandidateCommandHandler {

  private final JobOfferRepository jobOfferRepository;

  public void doHandle(final HireCandidateCommand command) {
    this.jobOfferRepository.hireCandidate(command.getCandidateId());
  }
}
