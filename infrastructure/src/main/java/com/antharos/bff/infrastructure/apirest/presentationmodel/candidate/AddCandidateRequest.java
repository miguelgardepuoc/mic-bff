package com.antharos.bff.infrastructure.apirest.presentationmodel.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class AddCandidateRequest {
  String id;
  String jobOfferId;
  String personalEmail;
  MultipartFile cv;
}
