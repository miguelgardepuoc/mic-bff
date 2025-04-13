package com.antharos.bff.domain.candidate;

import com.antharos.bff.domain.globalexceptions.NotFoundException;

public class CandidateNotFound extends NotFoundException {
  public CandidateNotFound(String message) {
    super(message);
  }
}
