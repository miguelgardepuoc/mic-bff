package com.antharos.bff.domain.repository;

import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import java.util.List;
import java.util.UUID;

public interface JobOfferRepository {
  List<SimpleJobOffer> findAll();

  JobOffer findById(UUID id);

  boolean existsByEmail(String personalEmail);

  void addCandidate(UUID candidateId, UUID jobOfferId, String personalEmail, String fileUrl);

  void hireCandidate(String candidateId);

  void interviewCandidate(String candidateId);

  void rejectCandidate(String candidateId);

  List<Candidate> findByJobOfferId(UUID jobOfferId);

  void addJobOffer(JobOffer jobOffer);

  void withdrawJobOffer(String id);

  void update(JobOffer jobOffer);
}
