package com.antharos.bff.application.commands.candidate.interview;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class InterviewCandidateCommand {
  String candidateId;
}
