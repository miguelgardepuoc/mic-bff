package com.antharos.bff.infrastructure.out.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCandidateRequest {
  String id;
  String jobOfferId;
  String personalEmail;
  String cvFilename;
}
