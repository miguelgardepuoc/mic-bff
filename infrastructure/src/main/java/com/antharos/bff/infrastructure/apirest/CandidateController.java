package com.antharos.bff.infrastructure.apirest;

import com.antharos.bff.application.create.AddCandidateCommand;
import com.antharos.bff.application.create.AddCandidateCommandHandler;
import com.antharos.bff.application.find.FindCandidatesByJobOfferQuery;
import com.antharos.bff.application.find.FindCandidatesByJobOfferQueryHandler;
import com.antharos.bff.application.update.*;
import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.infrastructure.apirest.presentationmodel.CandidateMapper;
import com.antharos.bff.infrastructure.apirest.presentationmodel.SimpleCandidateResponse;
import java.io.IOException;
import java.util.List;
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
  private final RejectCandidateCommandHandler rejectCandidateCommandHandler;
  private final HireCandidateCommandHandler hireCandidateCommandHandler;
  private final InterviewCandidateCommandHandler interviewCandidateCommandHandler;
  private final FindCandidatesByJobOfferQueryHandler findCandidatesByJobOfferQueryHandler;
  private final CandidateMapper mapper;

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

  @GetMapping
  public ResponseEntity<List<SimpleCandidateResponse>> findByJobOfferId(
      @RequestParam UUID jobOfferId) {
    List<Candidate> candidates =
        this.findCandidatesByJobOfferQueryHandler.handle(
            FindCandidatesByJobOfferQuery.of(jobOfferId));

    if (candidates.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(this.mapper.toSimpleCandidatesResponse(candidates));
  }

  @PatchMapping("/{candidateId}/reject")
  public ResponseEntity<Void> rejectCandidate(@PathVariable String candidateId) {
    this.rejectCandidateCommandHandler.doHandle(
        RejectCandidateCommand.builder().candidateId(candidateId).byUser("admin").build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/{candidateId}/hire")
  public ResponseEntity<Void> hireCandidate(@PathVariable String candidateId) {
    this.hireCandidateCommandHandler.doHandle(
        HireCandidateCommand.builder().candidateId(candidateId).byUser("admin").build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/{candidateId}/interview")
  public ResponseEntity<Void> interviewCandidate(@PathVariable String candidateId) {
    this.interviewCandidateCommandHandler.doHandle(
        InterviewCandidateCommand.builder().candidateId(candidateId).byUser("admin").build());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
