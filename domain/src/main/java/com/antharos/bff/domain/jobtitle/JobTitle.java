package com.antharos.bff.domain.jobtitle;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class JobTitle {

  private String id;

  private String description;

  private String photoUrl;
}
