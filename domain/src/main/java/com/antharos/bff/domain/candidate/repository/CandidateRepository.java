package com.antharos.bff.domain.candidate.repository;

import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.domain.candidate.CandidateId;
import java.util.Optional;

public interface CandidateRepository {
  Optional<Candidate> findBy(CandidateId candidateId);
}
