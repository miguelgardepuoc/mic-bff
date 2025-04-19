package com.antharos.bff.application.create;

import com.antharos.bff.domain.candidate.CandidateAlreadyRegisteredException;
import com.antharos.bff.domain.joboffer.JobOfferNotFound;
import com.antharos.bff.domain.repository.BlobRepository;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddCandidateCommandHandler {
  private final JobOfferRepository jobOfferRepository;
  private final BlobRepository blobRepository;

  public void doHandle(final AddCandidateCommand command) throws IOException {
    if (this.jobOfferRepository.findById(command.getJobOfferId()) == null) {
      throw new JobOfferNotFound(command.getJobOfferId());
    }
    if (this.jobOfferRepository.existsByEmail(command.getPersonalEmail())) {
      throw new CandidateAlreadyRegisteredException(command.getPersonalEmail());
    }
    final String cvFilename = this.blobRepository.uploadFile(command.getCv());
    this.jobOfferRepository.addCandidate(
        command.getCandidateId(), command.getJobOfferId(), command.getPersonalEmail(), cvFilename);
  }
}
