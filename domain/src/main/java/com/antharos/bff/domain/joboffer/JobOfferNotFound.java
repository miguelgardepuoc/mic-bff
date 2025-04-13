package com.antharos.bff.domain.joboffer;

import com.antharos.bff.domain.globalexceptions.NotFoundException;

public class JobOfferNotFound extends NotFoundException {
  public JobOfferNotFound(String message) {
    super(message);
  }
}
