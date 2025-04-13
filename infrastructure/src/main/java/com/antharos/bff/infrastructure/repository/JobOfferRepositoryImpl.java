package com.antharos.bff.infrastructure.repository;

import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import com.antharos.bff.domain.repository.JobOfferRepository;
import com.antharos.bff.infrastructure.repository.model.AddCandidateRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
        this.restTemplate.getForObject(
            this.jobOfferApiUrl + "/api/job-offers", SimpleJobOffer[].class);
    assert jobOffers != null;
    return List.of(jobOffers);
  }

  @Override
  public JobOffer findById(UUID id) {
    String url = this.jobOfferApiUrl + "/api/job-offers" + "/" + id;

    return this.restTemplate.getForObject(url, JobOffer.class);
  }

  @Override
  public boolean existsByEmail(String personalEmail) {
    String url = this.jobOfferApiUrl + "/candidates/email/" + personalEmail;
    try {
      this.restTemplate.getForObject(url, Candidate.class);
      return true;
    } catch (HttpClientErrorException.NotFound e) {
      return false;
    }
  }

  @Override
  public void addCandidate(
      UUID candidateId, UUID jobOfferId, String personalEmail, String fileUrl) {
      final AddCandidateRequest request =
              new AddCandidateRequest(
                      candidateId, jobOfferId, personalEmail, fileUrl);

      this.restTemplate.postForObject(this.jobOfferApiUrl + "/candidates", request, Void.class);
  }
}
