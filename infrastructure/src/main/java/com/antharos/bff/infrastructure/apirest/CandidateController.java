package com.antharos.bff.infrastructure.apirest;

import com.antharos.bff.application.create.AddCandidateCommand;
import com.antharos.bff.application.create.AddCandidateCommandHandler;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {
  private final AddCandidateCommandHandler addCandidateCommandHandler;

  @PostMapping
  public ResponseEntity<Void> addCandidate(
      @RequestParam UUID id,
      @RequestParam UUID jobOfferId,
      @RequestParam String personalEmail,
      @RequestParam MultipartFile cv)
      throws IOException {
    AddCandidateCommand command =
        AddCandidateCommand.builder()
            .candidateId(id)
            .jobOfferId(jobOfferId)
            .personalEmail(personalEmail)
            .cv(cv)
            .build();

    this.addCandidateCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
