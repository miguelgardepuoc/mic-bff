package com.antharos.bff.application.create;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Value
public class AddCandidateCommand {
  UUID jobOfferId;
  UUID candidateId;
  String personalEmail;
  MultipartFile cv;
}
