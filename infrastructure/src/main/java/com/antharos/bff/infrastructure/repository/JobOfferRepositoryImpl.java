package com.antharos.bff.infrastructure.repository;

import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JobOfferRepositoryImpl implements JobOfferRepository {

  @Value("${job-offer.api.url}")
  private String jobOfferApiUrl;

  private final RestTemplate restTemplate;

  public JobOfferRepositoryImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<SimpleJobOffer> getJobOffers() {
    SimpleJobOffer[] jobOffers =
        this.restTemplate.getForObject(this.jobOfferApiUrl, SimpleJobOffer[].class);
    assert jobOffers != null;
    return List.of(jobOffers);
  }
}
