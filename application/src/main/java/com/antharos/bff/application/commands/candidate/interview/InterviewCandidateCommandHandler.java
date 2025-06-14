package com.antharos.bff.application.commands.candidate.interview;

import com.antharos.bff.domain.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewCandidateCommandHandler {

  private final JobOfferRepository jobOfferRepository;

  public void doHandle(final InterviewCandidateCommand command) {
    this.jobOfferRepository.interviewCandidate(command.getCandidateId());
  }
}
