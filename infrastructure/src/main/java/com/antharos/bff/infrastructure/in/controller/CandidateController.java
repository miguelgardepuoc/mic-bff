package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.commands.candidate.add.AddCandidateCommand;
import com.antharos.bff.application.commands.candidate.add.AddCandidateCommandHandler;
import com.antharos.bff.application.commands.candidate.hire.HireCandidateCommand;
import com.antharos.bff.application.commands.candidate.hire.HireCandidateCommandHandler;
import com.antharos.bff.application.commands.candidate.interview.InterviewCandidateCommand;
import com.antharos.bff.application.commands.candidate.interview.InterviewCandidateCommandHandler;
import com.antharos.bff.application.commands.candidate.reject.RejectCandidateCommand;
import com.antharos.bff.application.commands.candidate.reject.RejectCandidateCommandHandler;
import com.antharos.bff.application.queries.candidate.FindCandidatesByJobOfferQuery;
import com.antharos.bff.application.queries.candidate.FindCandidatesByJobOfferQueryHandler;
import com.antharos.bff.application.queries.candidate.FindSasUrlQuery;
import com.antharos.bff.application.queries.candidate.FindSasUrlQueryHandler;
import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.infrastructure.in.dto.candidate.CandidateMapper;
import com.antharos.bff.infrastructure.in.dto.candidate.SimpleCandidateResponse;
import com.antharos.bff.infrastructure.security.ManagementOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidate", description = "Operations related to job offer candidates")
public class CandidateController {
  private final AddCandidateCommandHandler addCandidateCommandHandler;
  private final RejectCandidateCommandHandler rejectCandidateCommandHandler;
  private final HireCandidateCommandHandler hireCandidateCommandHandler;
  private final InterviewCandidateCommandHandler interviewCandidateCommandHandler;
  private final FindCandidatesByJobOfferQueryHandler findCandidatesByJobOfferQueryHandler;
  private final FindSasUrlQueryHandler findSasUrlQueryHandler;
  private final CandidateMapper mapper;

  @PermitAll
  @PostMapping
  @Operation(
      summary = "Add a new candidate",
      description = "Registers a new candidate for a job offer")
  @ApiResponse(responseCode = "201", description = "Candidate successfully added")
  public ResponseEntity<Void> addCandidate(
      @RequestParam String id,
      @RequestParam String jobOfferId,
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

  @ManagementOnly
  @GetMapping
  @Operation(
      summary = "Get candidates by job offer",
      description = "List all candidates who applied to a given job offer")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Candidates found",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SimpleCandidateResponse.class))),
    @ApiResponse(responseCode = "204", description = "No candidates found")
  })
  public ResponseEntity<List<SimpleCandidateResponse>> findByJobOfferId(
      @RequestParam String jobOfferId) {
    List<Candidate> candidates =
        this.findCandidatesByJobOfferQueryHandler.handle(
            FindCandidatesByJobOfferQuery.of(jobOfferId));

    if (candidates == null || candidates.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(this.mapper.toSimpleCandidatesResponse(candidates));
  }

  @ManagementOnly
  @PatchMapping("/{candidateId}/reject")
  @Operation(
      summary = "Reject candidate",
      description = "Rejects a candidate from a job offer process")
  @ApiResponse(responseCode = "200", description = "Candidate rejected successfully")
  public ResponseEntity<Void> rejectCandidate(@PathVariable String candidateId) {
    this.rejectCandidateCommandHandler.doHandle(
        RejectCandidateCommand.builder().candidateId(candidateId).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ManagementOnly
  @PatchMapping("/{candidateId}/hire")
  @Operation(summary = "Hire candidate", description = "Marks a candidate as hired")
  @ApiResponse(responseCode = "200", description = "Candidate hired successfully")
  public ResponseEntity<Void> hireCandidate(@PathVariable String candidateId) {
    this.hireCandidateCommandHandler.doHandle(
        HireCandidateCommand.builder().candidateId(candidateId).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ManagementOnly
  @PatchMapping("/{candidateId}/interview")
  @Operation(summary = "Interview candidate", description = "Marks a candidate as interviewed")
  @ApiResponse(responseCode = "200", description = "Candidate interviewed successfully")
  public ResponseEntity<Void> interviewCandidate(@PathVariable String candidateId) {
    this.interviewCandidateCommandHandler.doHandle(
        InterviewCandidateCommand.builder().candidateId(candidateId).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ManagementOnly
  @GetMapping("/sas-url")
  @Operation(
      summary = "Get SAS URL",
      description = "Generates a secure Azure Blob Storage SAS URL for the given file")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "SAS URL generated successfully",
        content =
            @Content(
                mediaType = "application/json",
                schema =
                    @Schema(
                        example =
                            "{\"url\": \"https://storageaccount.blob.core.windows.net/...\"}"))),
    @ApiResponse(responseCode = "400", description = "Bad request - missing or invalid filename"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Map<String, String>> getSasUrl(
      @RequestParam
          @Parameter(
              description = "The name of the file to generate the SAS URL for",
              required = true)
          String filename) {
    String sasUrl = this.findSasUrlQueryHandler.handle(FindSasUrlQuery.of(filename));
    return ResponseEntity.ok(Map.of("url", sasUrl));
  }
}
