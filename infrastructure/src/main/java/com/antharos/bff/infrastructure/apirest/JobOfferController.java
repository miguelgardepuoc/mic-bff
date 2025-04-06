package com.antharos.bff.infrastructure.apirest;

import com.antharos.bff.infrastructure.apirest.presentationmodel.JobOfferMapper;
import com.antharos.bff.infrastructure.apirest.presentationmodel.SimpleJobOffer;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-offers")
@RequiredArgsConstructor
public class JobOfferController {

  private final JobOfferMapper mapper;

  @GetMapping
  public ResponseEntity<List<SimpleJobOffer>> getAllJobOffers() {
    return ResponseEntity.ok(new ArrayList<>());
  }
}
