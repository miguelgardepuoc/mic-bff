package com.antharos.bff.domain.joboffer;

import com.antharos.bff.domain.globalexceptions.NotFoundException;

public class JobOfferNotFoundException extends NotFoundException {
  public JobOfferNotFoundException(String message) {
    super(message);
  }
}
