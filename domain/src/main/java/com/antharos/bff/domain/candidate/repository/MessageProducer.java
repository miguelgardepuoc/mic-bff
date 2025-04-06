package com.antharos.bff.domain.candidate.repository;

import com.antharos.bff.domain.candidate.Candidate;
import java.util.UUID;

public interface MessageProducer {
  void sendMessage(UUID id, final String subject, Candidate candidate);

  void sendUserHiredMessage(Candidate candidate);
}
