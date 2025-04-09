package com.antharos.bff.domain.repository;

import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import java.util.List;

public interface JobOfferRepository {
  List<SimpleJobOffer> findAll();
}
