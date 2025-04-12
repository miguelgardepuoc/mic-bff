package com.antharos.bff.domain.jobtitle;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class JobTitle {

  private UUID id;

  private String description;

  private String photoUrl;
}
