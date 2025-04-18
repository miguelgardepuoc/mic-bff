package com.antharos.bff.infrastructure.apirest;

import com.antharos.bff.application.create.AddJobOfferCommand;
import com.antharos.bff.application.create.AddJobOfferCommandHandler;
import com.antharos.bff.application.delete.WithdrawJobOfferCommand;
import com.antharos.bff.application.delete.WithdrawJobOfferCommandHandler;
import com.antharos.bff.application.find.FindJobOfferQuery;
import com.antharos.bff.application.find.FindJobOfferQueryHandler;
import com.antharos.bff.application.find.FindJobOffersQuery;
import com.antharos.bff.application.find.FindJobOffersQueryHandler;
import com.antharos.bff.application.update.UpdateJobOfferCommand;
import com.antharos.bff.application.update.UpdateJobOfferCommandHandler;
import com.antharos.bff.infrastructure.apirest.presentationmodel.*;

import java.util.List;
import java.util.UUID;
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
            this.findJobOffersQueryHandler.handle(FindJobOffersQuery.of()).stream().toList()));
  }

  @GetMapping("/{jobOfferId}")
  public ResponseEntity<JobOfferResponse> findJobOfferDetail(@PathVariable UUID jobOfferId) {
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
    WithdrawJobOfferCommand command =
            WithdrawJobOfferCommand.builder().id(jobOfferId).build();

    this.withdrawJobOfferCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
