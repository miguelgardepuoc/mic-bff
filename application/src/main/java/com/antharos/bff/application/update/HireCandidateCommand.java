package com.antharos.bff.application.update;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class HireCandidateCommand {
  String candidateId;
  String byUser;
}
