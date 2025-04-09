package com.antharos.bff.application.find;

import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindJobOffersQueryHandler {
  private final JobOfferRepository jobOfferRepository;

  public List<SimpleJobOffer> handle(FindJobOffersQuery query) {
    return this.jobOfferRepository.findAll();
  }
}
