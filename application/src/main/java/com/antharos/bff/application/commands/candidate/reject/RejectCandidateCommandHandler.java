package com.antharos.bff.application.commands.candidate.reject;

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
