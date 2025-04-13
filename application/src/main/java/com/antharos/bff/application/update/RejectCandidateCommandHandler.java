package com.antharos.bff.application.update;

import com.antharos.bff.domain.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RejectCandidateCommandHandler {

  private final JobOfferRepository jobOfferRepository;

  public void doHandle(final RejectCandidateCommand command) {
    this.jobOfferRepository.rejectCandidate(command.getCandidateId());
  }
}
