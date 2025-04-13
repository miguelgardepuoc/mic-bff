package com.antharos.bff.application.update;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RejectCandidateCommand {
  String candidateId;
  String byUser;
}
