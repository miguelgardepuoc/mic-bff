package com.antharos.bff.domain.repository;

import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import java.util.List;

public interface JobOfferRepository {
  List<SimpleJobOffer> findAll();

  JobOffer findById(String id);

  boolean existsByEmail(String personalEmail);

  void addCandidate(String candidateId, String jobOfferId, String personalEmail, String fileUrl);

  void hireCandidate(String candidateId);

  void interviewCandidate(String candidateId);

  void rejectCandidate(String candidateId);

  List<Candidate> findByJobOfferId(String jobOfferId);

  void addJobOffer(JobOffer jobOffer);

  void withdrawJobOffer(String id);

  void update(JobOffer jobOffer);
}
