package com.antharos.bff.domain.department;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Department {
  private String id;
  private String description;
  private String departmentHeadUserId;
}
