package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.commands.joboffer.add.AddJobOfferCommand;
import com.antharos.bff.application.commands.joboffer.add.AddJobOfferCommandHandler;
import com.antharos.bff.application.commands.joboffer.withdraw.WithdrawJobOfferCommand;
import com.antharos.bff.application.commands.joboffer.withdraw.WithdrawJobOfferCommandHandler;
import com.antharos.bff.application.queries.joboffer.FindJobOfferQuery;
import com.antharos.bff.application.queries.joboffer.FindJobOfferQueryHandler;
import com.antharos.bff.application.queries.joboffer.FindJobOffersQueryHandler;
import com.antharos.bff.application.commands.joboffer.update.UpdateJobOfferCommand;
import com.antharos.bff.application.commands.joboffer.update.UpdateJobOfferCommandHandler;
import com.antharos.bff.infrastructure.in.dto.joboffer.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-offers")
@RequiredArgsConstructor
public class JobOfferController {

  private final FindJobOffersQueryHandler findJobOffersQueryHandler;
  private final FindJobOfferQueryHandler findJobOfferQueryHandler;

  private final AddJobOfferCommandHandler addJobOfferCommandHandler;
  private final UpdateJobOfferCommandHandler updateJobOfferCommandHandler;
  private final WithdrawJobOfferCommandHandler withdrawJobOfferCommandHandler;

  private final JobOfferMapper mapper;

  @GetMapping
  public ResponseEntity<List<SimpleJobOfferResponse>> findJobOffers() {
    return ResponseEntity.ok(
        this.mapper.toSimpleJobOffers(
            this.findJobOffersQueryHandler.handle().stream().toList()));
  }

  @GetMapping("/{jobOfferId}")
  public ResponseEntity<JobOfferResponse> findJobOfferDetail(@PathVariable String jobOfferId) {
    return this.findJobOfferQueryHandler
        .handle(FindJobOfferQuery.of(jobOfferId))
        .map(this.mapper::toJobOfferResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
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

  @PutMapping
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

  @DeleteMapping("/{jobOfferId}")
  public ResponseEntity<Void> withdrawJobOffer(@PathVariable String jobOfferId) {
    WithdrawJobOfferCommand command = WithdrawJobOfferCommand.builder().id(jobOfferId).build();

    this.withdrawJobOfferCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
