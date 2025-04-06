package com.antharos.bff.domain.candidate;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Candidate {

  private CandidateId id;

  private String personalEmail;

  private String cv;

  private Name name;

  private Surname surname;

  private TelephoneNumber telephoneNumber;

  private Status status;

  private String createdBy;

  private Date createdAt;

  private String lastModifiedBy;

  private Date lastModifiedAt;

  public Candidate(CandidateId id) {
    this.id = id;
  }
}
