package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.commands.joboffer.add.AddJobOfferCommand;
import com.antharos.bff.application.commands.joboffer.add.AddJobOfferCommandHandler;
import com.antharos.bff.application.commands.joboffer.update.UpdateJobOfferCommand;
import com.antharos.bff.application.commands.joboffer.update.UpdateJobOfferCommandHandler;
import com.antharos.bff.application.commands.joboffer.withdraw.WithdrawJobOfferCommand;
import com.antharos.bff.application.commands.joboffer.withdraw.WithdrawJobOfferCommandHandler;
import com.antharos.bff.application.queries.joboffer.FindJobOfferQuery;
import com.antharos.bff.application.queries.joboffer.FindJobOfferQueryHandler;
import com.antharos.bff.application.queries.joboffer.FindJobOffersQueryHandler;
import com.antharos.bff.infrastructure.in.dto.joboffer.*;
import com.antharos.bff.infrastructure.security.ManagementOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-offers")
@RequiredArgsConstructor
@Tag(name = "Job Offer", description = "Operations related to job offers")
public class JobOfferController {

  private final FindJobOffersQueryHandler findJobOffersQueryHandler;
  private final FindJobOfferQueryHandler findJobOfferQueryHandler;

  private final AddJobOfferCommandHandler addJobOfferCommandHandler;
  private final UpdateJobOfferCommandHandler updateJobOfferCommandHandler;
  private final WithdrawJobOfferCommandHandler withdrawJobOfferCommandHandler;

  private final JobOfferMapper mapper;

  @PermitAll
  @GetMapping
  @Operation(
          summary = "Get all job offers",
          description = "Retrieves a list of all active job offers")
  @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content =
          @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SimpleJobOfferResponse.class)))
  public ResponseEntity<List<SimpleJobOfferResponse>> findJobOffers() {
    return ResponseEntity.ok(
        this.mapper.toSimpleJobOffers(this.findJobOffersQueryHandler.handle().stream().toList()));
  }

  @PermitAll
  @GetMapping("/{jobOfferId}")
  @Operation(
          summary = "Get job offer details",
          description = "Retrieve full details of a specific job offer by ID")
  @ApiResponses({
          @ApiResponse(
                  responseCode = "200",
                  description = "Job offer found",
                  content =
                  @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = JobOfferResponse.class))),
          @ApiResponse(responseCode = "404", description = "Job offer not found")
  })
  public ResponseEntity<JobOfferResponse> findJobOfferDetail(@PathVariable String jobOfferId) {
    return this.findJobOfferQueryHandler
        .handle(FindJobOfferQuery.of(jobOfferId))
        .map(this.mapper::toJobOfferResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @ManagementOnly
  @PostMapping
  @Operation(
          summary = "Add a new job offer",
          description = "Creates a new job offer with the provided details")
  @ApiResponse(responseCode = "201", description = "Job offer created successfully")
  public ResponseEntity<Void> addJobOffer(@RequestBody AddJobOfferRequest request) {
    AddJobOfferCommand command =
        AddJobOfferCommand.builder()
            .id(request.getId())
            .jobTitleId(request.getJobTitleId())
            .description(request.getDescription())
            .remote(request.getRemote())
            .requirement(request.getRequirement())
            .minSalary(request.getMinSalary())
            .maxSalary(request.getMaxSalary())
            .build();

    this.addJobOfferCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @ManagementOnly
  @PutMapping
  @Operation(summary = "Update a job offer", description = "Modifies an existing job offer")
  @ApiResponse(responseCode = "200", description = "Job offer updated successfully")
  public ResponseEntity<Void> updateJobOffer(@RequestBody UpdateJobOfferRequest request) {
    UpdateJobOfferCommand command =
        UpdateJobOfferCommand.builder()
            .id(request.getId())
            .description(request.getDescription())
            .remote(request.getRemote())
            .requirement(request.getRequirement())
            .minSalary(request.getMinSalary())
            .maxSalary(request.getMaxSalary())
            .build();

    this.updateJobOfferCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ManagementOnly
  @DeleteMapping("/{jobOfferId}")
  @Operation(summary = "Withdraw a job offer", description = "Marks a job offer as withdrawn by ID")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Job offer withdrawn successfully"),
          @ApiResponse(responseCode = "404", description = "Job offer not found")
  })
  public ResponseEntity<Void> withdrawJobOffer(@PathVariable String jobOfferId) {
    WithdrawJobOfferCommand command = WithdrawJobOfferCommand.builder().id(jobOfferId).build();

    this.withdrawJobOfferCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
