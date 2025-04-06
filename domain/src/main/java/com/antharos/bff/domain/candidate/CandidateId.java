package com.antharos.bff.domain.candidate;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CandidateId {

  String valueAsString;

  public static CandidateId of(String candidateId) {
    try {
      UUID.fromString(candidateId);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "CandidateId must be a valid UUID. Invalid string value: " + candidateId);
    }
    return new CandidateId(candidateId);
  }
}
