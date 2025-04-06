package com.antharos.bff.domain.joboffer;

import com.antharos.bff.domain.globalexceptions.AlreadyExistsException;

public class JobOfferAlreadyExists extends AlreadyExistsException {
  public JobOfferAlreadyExists() {
    super("Job offer already exists");
  }
}
