package com.antharos.bff.infrastructure.apirest;

import com.antharos.bff.application.find.FindJobOffersQuery;
import com.antharos.bff.application.find.FindJobOffersQueryHandler;
import com.antharos.bff.infrastructure.apirest.presentationmodel.JobOfferMapper;
import com.antharos.bff.infrastructure.apirest.presentationmodel.SimpleJobOfferDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-offers")
@RequiredArgsConstructor
public class JobOfferController {

  private final FindJobOffersQueryHandler findJobOffersQueryHandler;

  private final JobOfferMapper mapper;

  @GetMapping
  public ResponseEntity<List<SimpleJobOfferDto>> getAllJobOffers() {
    return ResponseEntity.ok(
        this.mapper.toSimpleJobOffers(
            this.findJobOffersQueryHandler.handle(FindJobOffersQuery.of()).stream().toList()));
  }
}
