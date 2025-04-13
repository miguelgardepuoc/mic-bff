package com.antharos.bff.domain.candidate;

public class CandidateAlreadyRegisteredException extends RuntimeException {
  public CandidateAlreadyRegisteredException(String message) {
    super(message);
  }
}
