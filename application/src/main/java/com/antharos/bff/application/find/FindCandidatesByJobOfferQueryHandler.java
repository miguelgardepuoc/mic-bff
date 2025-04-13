package com.antharos.bff.application.find;

import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindCandidatesByJobOfferQueryHandler {
  private final JobOfferRepository repository;

  public List<Candidate> handle(FindCandidatesByJobOfferQuery query) {
    return this.repository.findByJobOfferId(query.getJobOfferId());
  }
}
