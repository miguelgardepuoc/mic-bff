package com.antharos.bff.infrastructure.repository;

import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import com.antharos.bff.domain.repository.JobOfferRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JobOfferRepositoryImpl implements JobOfferRepository {

  private final RestTemplate restTemplate;

  @Value("${rest-client.job-offer.host}")
  private String jobOfferApiUrl;

  public JobOfferRepositoryImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<SimpleJobOffer> findAll() {
    SimpleJobOffer[] jobOffers =
        this.restTemplate.getForObject(this.jobOfferApiUrl, SimpleJobOffer[].class);
    assert jobOffers != null;
    return List.of(jobOffers);
  }

  @Override
  public JobOffer findById(UUID id) {
    String url = this.jobOfferApiUrl + "/" + id;

    return this.restTemplate.getForObject(url, JobOffer.class);
  }
}
