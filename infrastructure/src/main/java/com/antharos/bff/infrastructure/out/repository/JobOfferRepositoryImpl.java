package com.antharos.bff.infrastructure.out.repository;

import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import com.antharos.bff.domain.repository.JobOfferRepository;
import com.antharos.bff.infrastructure.out.repository.model.AddCandidateRequest;
import com.antharos.bff.infrastructure.out.repository.model.CandidatesMapper;
import com.antharos.bff.infrastructure.out.repository.model.FindCandidatesResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JobOfferRepositoryImpl implements JobOfferRepository {

  private final WebClient jobOfferWebClient;

  public JobOfferRepositoryImpl(@Qualifier("jobOfferWebClient") WebClient jobOfferWebClient) {
    this.jobOfferWebClient = jobOfferWebClient;
  }

  @Override
  public List<SimpleJobOffer> findAll() {
    return jobOfferWebClient
        .get()
        .uri("/job-offers")
        .retrieve()
        .bodyToMono(SimpleJobOffer[].class)
        .map(List::of)
        .block();
  }

  @Override
  public JobOffer findById(String id) {
    return jobOfferWebClient
        .get()
        .uri("/job-offers/{id}", id)
        .retrieve()
        .bodyToMono(JobOffer.class)
        .block();
  }

  @Override
  public void addCandidate(
      String candidateId, String jobOfferId, String personalEmail, String fileUrl) {
    AddCandidateRequest request =
        new AddCandidateRequest(candidateId, jobOfferId, personalEmail, fileUrl);
    jobOfferWebClient
        .post()
        .uri("/candidates")
        .bodyValue(request)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void hireCandidate(String candidateId) {
    jobOfferWebClient
        .patch()
        .uri("/candidates/{id}/hire", candidateId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void interviewCandidate(String candidateId) {
    jobOfferWebClient
        .patch()
        .uri("/candidates/{id}/interview", candidateId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void rejectCandidate(String candidateId) {
    jobOfferWebClient
        .patch()
        .uri("/candidates/{id}/reject", candidateId)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public List<Candidate> findByJobOfferId(String jobOfferId) {
    return jobOfferWebClient
        .get()
        .uri("/candidates?jobOfferId={jobOfferId}", jobOfferId)
        .retrieve()
        .bodyToMono(FindCandidatesResponse[].class)
        .map(candidates -> CandidatesMapper.toDomainList(List.of(candidates)))
        .block();
  }

  @Override
  public void addJobOffer(JobOffer jobOffer) {
    jobOfferWebClient
        .post()
        .uri("/job-offers")
        .bodyValue(jobOffer)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void withdrawJobOffer(String id) {
    jobOfferWebClient.delete().uri("/job-offers/{id}", id).retrieve().toBodilessEntity().block();
  }

  @Override
  public void update(JobOffer jobOffer) {
    jobOfferWebClient
        .put()
        .uri("/job-offers")
        .bodyValue(jobOffer)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public Candidate findCandidateById(String candidateId) {
    return this.jobOfferWebClient
        .get()
        .uri("/candidates/{id}", candidateId)
        .retrieve()
        .bodyToMono(Candidate.class)
        .block();
  }
}
