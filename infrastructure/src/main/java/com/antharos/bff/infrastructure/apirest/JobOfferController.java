package com.antharos.bff.infrastructure.apirest;

import com.antharos.bff.application.find.FindJobOfferQuery;
import com.antharos.bff.application.find.FindJobOfferQueryHandler;
import com.antharos.bff.application.find.FindJobOffersQuery;
import com.antharos.bff.application.find.FindJobOffersQueryHandler;
import com.antharos.bff.infrastructure.apirest.presentationmodel.JobOfferMapper;
import com.antharos.bff.infrastructure.apirest.presentationmodel.JobOfferResponse;
import com.antharos.bff.infrastructure.apirest.presentationmodel.SimpleJobOfferResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-offers")
@RequiredArgsConstructor
public class JobOfferController {

  private final FindJobOffersQueryHandler findJobOffersQueryHandler;

  private final FindJobOfferQueryHandler findJobOfferQueryHandler;

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
}
