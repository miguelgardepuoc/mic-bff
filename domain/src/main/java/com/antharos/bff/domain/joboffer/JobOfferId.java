package com.antharos.bff.domain.joboffer;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JobOfferId {

  String valueAsString;

  public static JobOfferId of(String jobOfferId) {
    try {
      UUID.fromString(jobOfferId);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "JobOfferId must be a valid UUID. Invalid string value: " + jobOfferId);
    }
    return new JobOfferId(jobOfferId);
  }
}
