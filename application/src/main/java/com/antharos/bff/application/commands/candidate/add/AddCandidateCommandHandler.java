package com.antharos.bff.application.commands.candidate.add;

import com.antharos.bff.domain.joboffer.JobOfferNotFoundException;
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
      throw new JobOfferNotFoundException(command.getJobOfferId());
    }
    final String cvFilename = this.blobRepository.uploadFile(command.getCv());
    this.jobOfferRepository.addCandidate(
        command.getCandidateId(), command.getJobOfferId(), command.getPersonalEmail(), cvFilename);
  }
}
