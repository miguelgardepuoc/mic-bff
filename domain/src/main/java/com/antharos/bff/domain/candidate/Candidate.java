package com.antharos.bff.domain.candidate;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Candidate {
  private UUID id;
  private String personalEmail;
  private String fullName;
  private CandidateStatus status;
  private String cvFilename;
}
