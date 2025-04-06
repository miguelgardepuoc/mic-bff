package com.antharos.bff.domain.joboffer;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class JobOffer {

  private final JobOfferId id;

  private UUID jobTitleId;

  private String description;

  private SalaryRange salaryRange;

  private byte[] photo;

  private Float remote;

  private String requirement;

  private boolean isActive;

  private String createdBy;

  private Date createdAt;

  private String lastModifiedBy;

  private Date lastModifiedAt;

  public JobOffer(JobOfferId id) {
    this.id = id;
  }
}
