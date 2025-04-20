package com.antharos.bff.application.commands.candidate.add;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Value
public class AddCandidateCommand {
  String jobOfferId;
  String candidateId;
  String personalEmail;
  MultipartFile cv;
}
